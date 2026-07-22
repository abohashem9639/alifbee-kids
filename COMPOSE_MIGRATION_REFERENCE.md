# Talapati Android App — Architecture & Conventions Reference

> **Purpose of this document:** This is the reference guide describing exactly how the Talapati app (a 100% Jetpack Compose app, zero XML layouts) is built. When migrating another Kotlin + XML app to Jetpack Compose, replicate the architecture, patterns, naming, and conventions described here so both apps stay consistent.

---

## 1. Project Overview

- **App:** Talapati (`com.talapati.app`) — food/grocery delivery app (dashboard → service types → restaurants/single-branch stores/delivery, cart, checkout, orders, favourites, coupons, addresses with Google Maps, auth with guest mode + OTP password reset).
- **UI:** 100% Jetpack Compose + Material 3. There are **no XML layouts, no Fragments, no findViewById, no data binding**. XML exists only for resources (drawables, strings, themes for splash, manifest).
- **Single-Activity architecture:** one `MainActivity`, everything else is composables + Navigation Compose.
- **Language support:** English + Arabic (full RTL support baked into navigation animations and layouts).

## 2. Tech Stack (exact versions from `gradle/libs.versions.toml`)

| Concern | Library | Version |
|---|---|---|
| Language | Kotlin (K2) | 2.2.0 |
| Build | AGP | 8.10.1, JVM target 11 |
| SDK | compileSdk 35, targetSdk 35, minSdk 24 | |
| UI | Compose BOM | 2025.06.01 |
| Design system | Material 3 (`androidx.compose.material3`) | from BOM |
| Navigation | Navigation Compose (type-safe routes) | 2.9.1 |
| DI | **Hilt** (hilt-android + KSP compiler, `androidx.hilt:hilt-navigation-compose`) — see §6; replaces the reference app's Koin 4.1 | Hilt 2.56+ / nav 1.2.0 |
| Networking | **Ktor client** (Android engine, ContentNegotiation, Logging, HttpTimeout) | 2.3.5 |
| Serialization | kotlinx-serialization-json | 1.9.0 |
| Persistence | **DataStore** (preferences) | 1.1.7 |
| Images | Coil (`coil-compose`) | 2.7.0 |
| Animations | Lottie Compose | 6.6.7 |
| Splash | androidx.core:core-splashscreen | 1.0.1 |
| Maps | Google Maps Compose (+utils, +widgets), play-services-location/maps | 6.7.1 |
| Date/time | kotlinx-datetime | 0.6.1 |
| Coroutines | kotlinx-coroutines (+play-services) | 1.10.2 |
| Misc | multidex, material-icons-extended, KSP | |

**Notably NOT used:** Retrofit/OkHttp (Ktor instead), Room (no local DB — server + DataStore only), RxJava (coroutines/Flow only), ViewBinding/DataBinding (nothing XML). **DI decision:** the reference Talapati codebase uses Koin 4.1, but the migrated app standardizes on **Hilt** — every Koin idiom you see in reference code must be translated using the mapping in §6.5.

All dependency versions live in a **Gradle version catalog** (`gradle/libs.versions.toml`) and every module references `libs.*` aliases. Kotlin DSL (`.kts`) everywhere.

## 3. Module Structure

```
android_app/
├── app/                  → all UI: screens, viewmodels, navigation, theme, common components
├── core/
│   ├── network/          → Ktor NetworkClient wrapper, BaseResDto/AppErrorDto (no feature knowledge)
│   └── datastore/        → DataStoreManager interface + impl (preferences), UserData model
├── data/                 → per-feature: repository + use cases + DTOs + mappers + data Koin modules
├── gradle/libs.versions.toml
└── settings.gradle.kts   → includes :app, :core:network, :core:datastore, :data
```

Dependency direction: `app → data → core:network / core:datastore`. The `app` module also depends on `core:datastore` directly (theme/locale prefs).

> Note: folders `datamodule/`, `feature/onboarding/`, `build-logic/` exist on disk but are **NOT included in settings.gradle.kts** — they are abandoned leftovers. Ignore them; do not replicate.

## 4. Architecture Pattern: MVI (Contract-based) + Clean-ish Data Layer

### 4.1 BaseViewModel (the core of the pattern)

Every screen ViewModel extends `BaseViewModel<Event, State, Effect>` (`app/ui/common/viewmodel/BaseViewModel.kt`):

```kotlin
interface UiState
interface UiEvent
interface UiEffect
abstract class UiFetchState(open val error: AppError? = null)

abstract class BaseViewModel<Event : UiEvent, State : UiState, Effect : UiEffect>(
    initialState: State
) : ViewModel() {
    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()          // state exposed as StateFlow

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()              // events from UI

    private val _effect = Channel<Effect>()
    val effect = _effect.receiveAsFlow()           // one-shot effects to UI

    abstract fun handleEvent(event: Event)

    init { subscribeEvents() }                     // collects `event` and calls handleEvent

    fun setEvent(event: Event) { viewModelScope.launch { _event.emit(event) } }
    protected fun setState(reduce: State.() -> State) { _uiState.value = uiState.value.reduce() }
    protected fun setEffect(builder: () -> Effect) { viewModelScope.launch { _effect.send(builder()) } }
}
```

### 4.2 Contract file — one per screen

Each screen has a `<Feature>Contract.kt` defining Event / State / Effect / FetchState in one place:

```kotlin
internal interface DashboardContract {
    sealed interface Event : UiEvent {
        data class OnBannerClicked(val banner: Banner) : Event
    }
    sealed interface Effect : UiEffect {
        data class NavigateToBannerAction(val action: BannerAction, val actionId: Long) : Effect
    }
    @Immutable
    data class State(
        val banners: List<Banner> = emptyList(),
        val fetchState: FetchState = FetchState.IsLoading(true),
    ) : UiState

    sealed class FetchState : UiFetchState() {
        data class IsLoading(val isLoading: Boolean) : FetchState()
        data class LoadData(override val error: AppError? = null) : FetchState()
    }
}
// convenience accessor defined next to the contract:
internal inline val DashboardContract.FetchState.loading: Boolean
    get() = if (this is DashboardContract.FetchState.IsLoading) this.isLoading else false
```

Conventions:
- `State` is an `@Immutable data class` with **default values for every property**.
- Loading/error handled via a nested `FetchState` sealed class carrying an optional `AppError`.
- Contract + ViewModel are `internal` to the app module.

### 4.3 ViewModel

```kotlin
@HiltViewModel
internal class DashboardViewModel @Inject constructor(
    private val addressUtils: AddressUtils,          // constructor injection via Hilt
    private val dataStoreManager: DataStoreManager
) : BaseViewModel<DashboardContract.Event, DashboardContract.State, DashboardContract.Effect>(
    initialState = DashboardContract.State()
) {
    init {
        setupDataFlows()        // collect DataStore/domain Flows → setState { copy(...) }
        fetchInitialData()      // one-shot loads in viewModelScope.launch
    }

    override fun handleEvent(event: DashboardContract.Event) {
        when (event) {
            is DashboardContract.Event.OnBannerClicked -> setEffect {
                DashboardContract.Effect.NavigateToBannerAction(event.banner.action, event.banner.actionId)
            }
        }
    }
}
```

- Use cases / repos / utils are injected through the constructor (no field injection).
- Data loading: `viewModelScope.launch { setState { copy(fetchState = IsLoading(true)) } ... }`.
- Results of use cases are `BaseRes<T>`; use `.onSuccess { } .onFailure { }` and map failures into `FetchState(error = ...)` or effects.

### 4.4 Screen composable — stateless, three standard parameters

Every screen receives exactly: `state`, `effectFlow`, `onEventSent`, plus navigation/interaction callbacks and sometimes `contentPadding` and `isRtl`. Screens **never own business state** and (in the preferred pattern) never see the NavController:

```kotlin
@Composable
internal fun LoginScreen(
    state: LoginContract.State,
    effectFlow: Flow<LoginContract.Effect>,
    onEventSent: (event: LoginContract.Event) -> Unit,
    contentPadding: PaddingValues,
    onBackClicked: () -> Unit,
    onSignInSuccessNavigate: () -> Unit,
    // ...more navigation callbacks
) {
    effectFlow.CollectEffect { effect ->        // one-shot effect collection helper
        when (effect) {
            LoginContract.Effect.SignInSuccessNavigate -> onSignInSuccessNavigate()
        }
    }

    if (state.fetchState.error != null) {
        ErrorModal(error = state.fetchState, onConfirmClicked = { onEventSent(LoginContract.Event.OnErrorDismissed) }, ...)
    }

    LoginContent(...)   // screen split into Screen (wiring) + Content (pure UI)
}
```

`CollectEffect` helper (`app/ui/common/ComposeUtils.kt`):

```kotlin
@Composable
fun <T> Flow<T>.CollectEffect(onEffect: suspend (effect: T) -> Unit) {
    LaunchedEffect(this) { this@CollectEffect.onEach { onEffect(it) }.collect {} }
}
```

Other screen conventions:
- Split into `XScreen` (state/effect wiring) + `XContent` (layout) where the screen is non-trivial.
- `@Preview` composables at the bottom of files wrapped in `TalapatiTheme { }` (~168 previews across the app).
- Local-only UI state uses `remember`/`rememberSaveable` inside the composable (e.g., text fields before submit).
- Click debouncing via `rememberClickDelay()` helper wrapping navigation/submit clicks (`clickDelay { ... }`).
- Loading dialog: `FullScreenLoaderDialog`; errors: `ErrorModal` fed by `FetchState`.

### 4.5 Per-screen folder layout inside `app` (replicate exactly)

```
app/src/main/kotlin/com/talapati/app/ui/screens/<feature>/
├── ui/<Feature>Screen.kt                     → stateless screen + Content + Previews
├── ui/components/...                         → components used only by this screen
├── ui/navigation/<Feature>Navigation.kt      → route object + NavGraphBuilder extension + navigate extension
└── ui/viewmodel/<Feature>Contract.kt
                 /<Feature>ViewModel.kt
```

> With Hilt there is **no per-screen `di/` folder**: `@HiltViewModel` registers the ViewModel automatically. (The reference Koin codebase has a `di/<Feature>Module.kt` in each screen folder — do not replicate those.)

Shared/reusable UI lives in `app/ui/common/` (≈50 components: `AppScaffold`, `AppTopBar`, `AppTextField` + `AppTextFieldType`, `AppSearchBar`, `RoundedButton`, `RoundedOutlinedButton`, `CompactRoundedButton`, `BottomSheet`, `ConfirmationDialog`, `ErrorModal`, `FullScreenLoaderDialog`, `EmptyState`, `QuantityStepper`, `SectionHeader`, `SelectableChip`, chips/carousels/cards for categories, products, places, orders, etc.). **Always reuse these instead of creating duplicates.**

## 5. Navigation (Navigation Compose, type-safe)

### 5.1 Route definitions — `@Serializable` objects/classes

```kotlin
@Serializable data object DashboardRoute : BottomBarNavRoute        // no args
@Serializable data class RestaurantDetailRoute(val restaurantId: String)  // with args
```

### 5.2 Per-feature navigation file pattern

```kotlin
// navigate extension (only for routes navigated from multiple places)
fun NavController.navigateToRestaurantDetail(restaurantId: String) {
    navigate(RestaurantDetailRoute(restaurantId = restaurantId))
}

// graph-builder extension: creates VM via hiltViewModel(), wires screen
fun NavGraphBuilder.restaurantDetailRoute(
    contentPadding: PaddingValues,
    navController: NavController,
    isRtl: Boolean,
    cartState: Cart?
) {
    slideComposable<RestaurantDetailRoute>(isRtlDirection = isRtl) { backStackEntry ->
        val viewModel: RestaurantViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsState()
        RestaurantDetailScreen(
            state = state,
            effectFlow = viewModel.effect,
            onEventSent = viewModel::setEvent,
            onBackClicked = { navController.popBackStack() },
            // args read via: backStackEntry.toRoute<RestaurantDetailRoute>().restaurantId
        )
    }
}
```

### 5.3 Transition helpers (`app/ui/utils/RouteAnimHelper.kt`)

Two custom `NavGraphBuilder` wrappers used for *every* destination (300 ms, `FastOutSlowInEasing`):
- `slideComposable<T>(isRtlDirection)` — horizontal slide push/pop, **direction flips for RTL**.
- `fadeComposable<T>` — fade through (used for bottom-bar destinations like Dashboard).

### 5.4 Single NavHost — all wiring in `TalapatiApp.kt`

- `TalapatiApp(appState, effectFlow, onEventSent)` is the root composable: creates `rememberNavController()`, wraps everything in `AppScaffold`, hosts the single `NavHost(startDestination = SplashRoute)` and registers every `xRoute(...)` extension, passing navigation lambdas down.
- **All navigation decisions live here** (which route to go to on success, popUpTo behavior, etc.). Screens only invoke callbacks.
- `isRtlDirection` computed from `LocalLayoutDirection` and passed to slide-animated routes.
- Global one-shot messages: `SnackbarHostState` + a `topSnackbarMessage: Int?` (string-res id auto-dismissed after 5s) for top banners.
- Helper: `NavController.popBackStackOrFinishAct()` — pops, or finishes the activity if the stack is empty (wrapped in `dropUnlessResumed`).
- Helper: `sharedViewModel<VM, R>()` — resolves a VM scoped to a parent back-stack entry (for flows sharing one VM, e.g., delivery checkout flow via `SharedRoute` marker). Hilt version: `hiltViewModel(viewModelStoreOwner = parentEntry)` where `parentEntry = remember(backStackEntry) { navController.getBackStackEntry(route) }`.

### 5.5 Bottom bar

- Marker interface `BottomBarNavRoute` on routes that show a bottom bar.
- `AppScaffold` picks one of **three bottom bars** based on the selected service-type classification (`Internal` → SingleBranchBottomNavBar, `External` → RestaurantBottomNavBar, `Delivery` → DeliveryBottomNavBar), each with its own route list (`bottomBarRouteSingleBranch`, etc.).
- Bottom-bar navigation uses `popUpTo(<home-of-section>) { saveState = true }`, `launchSingleTop = true`, `restoreState` for the home tab.

## 6. Dependency Injection — Hilt

> The original Talapati codebase uses Koin 4.1. **Decision for the migrated app: use Hilt.** Keep the same injection *shape* (constructor injection everywhere, one binding module per feature, app-wide `@Singleton`s) and translate every Koin idiom you see in reference code using the mapping table in §6.5.

### 6.1 Gradle setup

Add to `gradle/libs.versions.toml`:

```toml
[versions]
hilt = "2.56.2"                     # use latest stable
hiltNavigationCompose = "1.2.0"

[libraries]
hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
hilt-compiler = { group = "com.google.dagger", name = "hilt-android-compiler", version.ref = "hilt" }
androidx-hilt-navigation-compose = { group = "androidx.hilt", name = "hilt-navigation-compose", version.ref = "hiltNavigationCompose" }

[plugins]
hilt-android = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
```

Apply in **every module that declares Hilt bindings** (`:app`, `:data`, `:core:network`, `:core:datastore`):

```kotlin
plugins {
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.ksp)
}
dependencies {
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    // :app only:
    implementation(libs.androidx.hilt.navigation.compose)
}
```

### 6.2 Core annotations

- **Application:** `@HiltAndroidApp class MyApp : MultiDexApplication(), ImageLoaderFactory` — no manual module registration; Hilt discovers all `@Module @InstallIn` classes at compile time (this replaces the ~40-module `startKoin { modules(...) }` list).
- **Activity:** `@AndroidEntryPoint class MainActivity : ComponentActivity()`; global VM via `private val appViewModel: AppViewModel by viewModels()`; singleton utilities via `@Inject lateinit var themeManager: ThemeManager`, etc.
- **ViewModels:** `@HiltViewModel class XViewModel @Inject constructor(...) : BaseViewModel<...>` — **no per-screen DI module needed** (replaces every Koin `viewModelOf` module).
- **In composables:** `hiltViewModel()` (from `androidx.hilt.navigation.compose`) — replaces `koinViewModel()`.
- **Plain classes** (use cases, utils, `Impl` classes): just `@Inject constructor` — no module entry unless an interface must be bound.
- **Interface → implementation bindings:** one abstract `@Module` per feature with `@Binds @Singleton` (replaces `singleOf(::Impl).bind<Interface>()`).
- **Manually constructed objects** (NetworkClient, DataStore instance, app CoroutineScope): `@Provides @Singleton` in an `object` module.

### 6.3 Module examples

```kotlin
// data/src/.../auth/AuthDataModule.kt — interface bindings (replaces Koin authDataModule)
@Module
@InstallIn(SingletonComponent::class)
internal abstract class AuthDataModule {
    @Binds @Singleton
    abstract fun bindAuthRepo(impl: AuthRepoImpl): AuthRepo
}

// core/network — provider module
@Module
@InstallIn(SingletonComponent::class)
object NetworkCoreModule {
    @Provides @Singleton
    fun provideNetworkClient(): NetworkClient = NetworkClient()
}

// app-wide CoroutineScope (needed by LanguageUtilsImpl)
@Qualifier annotation class ApplicationScope

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {
    @Provides @Singleton @ApplicationScope
    fun provideAppScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
}
```

App-wide singletons to register this way: `ThemeManager`, `LanguageUtils`, `NavControllerState`, `DataStoreManager`, `NetworkClient`, `AddressUtils` (interface impls via `@Binds`, constructed objects via `@Provides`).

### 6.4 Gotcha: `attachBaseContext` runs before field injection

The locale wrapper is applied in `MainActivity.attachBaseContext`, but Hilt injects activity fields during `super.onCreate()`. Use an `@EntryPoint` there:

```kotlin
@EntryPoint
@InstallIn(SingletonComponent::class)
interface LocaleEntryPoint { fun languageUtils(): LanguageUtils }

override fun attachBaseContext(newBase: Context) {
    super.attachBaseContext(newBase)
    EntryPointAccessors.fromApplication(newBase, LocaleEntryPoint::class.java)
        .languageUtils()
        .setLocaleWithContextWrapper(this)
}
```

### 6.5 Koin → Hilt mapping (for translating reference-code patterns)

| Koin (reference codebase) | Hilt (migrated app) |
|---|---|
| `startKoin { modules(...) }` in `MyApp` | `@HiltAndroidApp` (auto-discovery — delete the module list) |
| `val xModule = module { viewModelOf(::XViewModel) }` | `@HiltViewModel` + `@Inject constructor` (module file deleted) |
| `singleOf(::RepoImpl).bind<Repo>()` | `@Binds @Singleton fun bindRepo(impl: RepoImpl): Repo` |
| `singleOf(::SomeUseCase)` | `@Inject constructor` on the use case (no module entry) |
| `koinViewModel()` in composables | `hiltViewModel()` |
| `by viewModel()` in Activity | `by viewModels()` |
| `getKoin().get<T>()` | `@Inject lateinit var` (or `@EntryPoint` before `onCreate`) |
| `koinViewModel(viewModelStoreOwner = parentEntry)` | `hiltViewModel(viewModelStoreOwner = parentEntry)` |

## 7. Data Layer (`:data` module) — per-feature Clean structure

```
data/src/main/kotlin/com/talapati/app/data/<feature>/
├── <Feature>DataModule.kt      → Hilt `@Module` with `@Binds` Repo→RepoImpl (see §6.3); use cases need no module (`@Inject constructor`)
├── data/                       → DTOs + mappers ("data" = wire layer)
│   ├── XxxDto.kt               → @Serializable, @SerialName on every field, nullable with defaults
│   └── XxxMapper.kt            → internal extension functions: Dto.toDomain(), DomainReq.toDto()
├── domain/                     → pure domain models + use cases
│   ├── Xxx.kt                  → plain data classes (what the UI consumes); domain models that get cached as JSON are @Serializable
│   └── DoSomethingUseCase.kt   → class with `suspend operator fun invoke(...)`
└── repository/
    ├── XxxRepo.kt              → interface
    └── XxxRepoImpl.kt          → internal class, endpoints as companion-object constants
```

### 7.1 Use case pattern

```kotlin
class SubmitUserLoginUseCase @Inject internal constructor(
    private val authRepo: AuthRepo,
    private val dataStoreManager: DataStoreManager
) {
    suspend operator fun invoke(loginReq: LoginReq): BaseRes<LoginRes> =
        authRepo.submitLogin(loginReq)
            .onSuccess { dataStoreManager.setUserData(...) }   // side effects on success
}
```

### 7.2 Repository pattern

```kotlin
internal class AuthRepoImpl @Inject constructor(private val networkClient: NetworkClient) : AuthRepo {
    companion object {
        private const val LOGIN = "${NetworkClient.BASE_URL_V1}/Auth/login"
    }
    override suspend fun submitLogin(loginReq: LoginReq) =
        networkClient.post<LoginResDto>(urlString = LOGIN, request = loginReq.toDto())
            .toBaseDomain { dto -> dto.toDomain() }     // BaseResDto<Dto> → BaseRes<Domain>
}
```

### 7.3 Result & error model

- Network layer returns `BaseResDto<T>` = `Body(body)` | `Error(AppErrorDto)`.
- Data layer converts to domain `BaseRes<T>` = `Body(body)` | `Error(AppError)` via `toBaseDomain(mapper)`.
- `AppError`: sealed — `Timeout, Network, Serialization, Unauthorized, Client, Server, Redirect, General(errorKey)` + companion with server error-key constants (`"invalid_coupon_error"`, `"out_of_stock_error"`, ...). ViewModels match on these keys to fire specific effects.
- Helpers: `onSuccess { }`, `onFailure { }`, `.body`, `.error`.

## 8. Network (`:core:network`) — Ktor

`NetworkClient` wraps a lazy `HttpClient(Android)` with:
- `HttpTimeout` 15s request/connect, `Logging` (LogLevel.BODY), `ContentNegotiation { json }` with `Json { ignoreUnknownKeys = true }`.
- Inline reified `get/post/put/delete<Dto>(urlString, request?, bearerToken?, customHeaders?)` methods; token passed per call via `bearerAuth` (token stored in DataStore and read by use cases/repos).
- `handleResponse`: reads body as text, 2xx → unwraps the server envelope `{"data": ...}` (`ApiResponse(data: JsonElement?)`) then decodes to `Dto`; non-2xx → decodes `ApiErrorResponse` and returns typed `AppErrorDto.General(errorKey)`; exceptions mapped via `AppErrorDto.fromThrowable` (timeout/IO/serialization/4xx/5xx).
- Base URL: `https://api.talabati.org/v1` as companion constants.

## 9. Persistence (`:core:datastore`)

- `DataStoreManager` interface exposing `Flow<...>` per key: `token, userData, userAddresses, userLoggedIn, selectedAddress, banners, serviceTypes, selectedServiceTypeId, darkTheme, storedLocale` + suspend setters (`setUserData`, `deleteUserData`, `setDarkTheme`, `setNewLocale`, ...).
- Backed by Preferences DataStore; keys in `keys/UserKeys.kt`; `DataStoreManagerImpl` bound to `DataStoreManager` via a Hilt `@Binds @Singleton` module in `:core:datastore`.
- **Complex objects (addresses, banners, service types, cart snapshots) are stored as JSON strings** (`Json.encodeToString` / `decodeFromString`) — domain models used this way are `@Serializable`.

## 10. Theming & Design System

- `TalapatiTheme(darkTheme: Boolean = isSystemInDarkTheme())` — Material 3 `lightColorScheme`/`darkColorScheme` fully specified in `ui/theme/Color.kt` (Material-Theme-Builder-style role names: `primaryLight`, `onPrimaryLight`, `surfaceContainerHighLight`, ... / `...Dark`). Primary is purple `#76239C`.
- **Extra brand colors** via `CustomColorsPalette` (`primaryFixed`, `skobeloff`, `black50`) exposed through `staticCompositionLocalOf` (`LocalCustomColors`) and provided inside the theme — access as `LocalCustomColors.current.skobeloff`.
- Typography: full Material 3 scale, Roboto family (`R.font.roboto`, `roboto_medium`).
- `Dimen.kt` for shared dimensions (shimmer sizes/shape).
- Dark mode is user-controlled: `ThemeManager` (Koin singleton) exposes `isDarkTheme: StateFlow<Boolean>` persisted in DataStore + provides `SystemBarStyle`s for edge-to-edge; toggled from Account Settings screen.
- Colors in composables come from `MaterialTheme.colorScheme.*` — never hardcoded.

## 11. Localization & RTL

- Full EN (`values/strings.xml`, ~304 strings) + AR (`values-ar/strings.xml`) coverage; `android:supportsRtl="true"`. All UI text via `stringResource(R.string...)`.
- `LocaleLanguage` enum (`En`, `Ar`). `LanguageUtils` (Koin singleton): `localeState: StateFlow<LocaleLanguage>`, `setDefaultLocale()` (stored → device → EN fallback), `setLocale(new)`, `setLocaleWithContextWrapper(context)` applied in `MainActivity.attachBaseContext`.
- On language change: `MainActivity` saves nav state into `NavControllerState` (Koin singleton holding a `Bundle`), then `recreateActivitySmoothly()`; on recreate, nav state is restored → user stays on the same screen.
- RTL awareness: `isRtl` derived from `LocalLayoutDirection` and passed into slide animations so push/pop directions mirror correctly.

## 12. App Startup Flow

1. **`MyApp`** (`MultiDexApplication`, `ImageLoaderFactory`): configures Coil (memory+disk cache, crossfade), `startKoin` with all modules, then `runBlocking { languageUtils.setDefaultLocale() }`.
2. **`MainActivity`** (single activity): `enableEdgeToEdge` with theme-aware system-bar styles → `installSplashScreen()` → collectors on `themeManager.isDarkTheme` and `languageUtils.localeState` (`repeatOnLifecycle(STARTED)`) → `setContent { TalapatiTheme(darkTheme) { TalapatiApp(appState, effect, ::setEvent) } }`.
3. **`AppViewModel`** (global VM, alive for the whole app): owns cross-screen state — cart (+item count, updating item, clear-cart dialog), addresses + selected address, service types + selected service type, theme flag, locale. Screens needing cart/address data receive slices of `appState` through `TalapatiApp` parameters rather than re-fetching.
4. Route flow: `SplashRoute` → (first launch) `OnBoardingRoute` → Login/SignUp/guest → `DashboardRoute` → service-type home (`SingleBranchHomeRoute` | `CuisinesRoute` | `DeliveryHome`) → detail/cart/checkout/etc.

## 13. Coding Conventions Summary (checklist for the migration)

1. Kotlin-only, coroutines + Flow everywhere; no LiveData, no RxJava.
2. One Contract file per screen (`Event`/`State`/`Effect`/`FetchState`); state `@Immutable` with defaults.
3. ViewModels extend `BaseViewModel`, constructor-injected, `internal`, per-screen Koin `viewModelOf` module.
4. Screens are stateless: `(state, effectFlow, onEventSent, callbacks...)`; Screen/Content split; previews in `TalapatiTheme`.
5. Navigation: `@Serializable` routes + `NavGraphBuilder.xRoute(...)` extensions + `NavController.navigateToX(...)` extensions; `slideComposable`/`fadeComposable` wrappers; all wiring centralized in the root app composable.
6. DI: Koin DSL (`viewModelOf`, `singleOf().bind<>()`), modules registered explicitly in the Application class.
7. Data: DTO (`@Serializable` + `@SerialName`, nullable fields) ≠ Domain model; mapping via `internal` extension functions `toDomain()`/`toDto()`; repos `internal` with endpoint constants; use cases with `operator fun invoke`.
8. Results: `BaseRes` sealed type with `onSuccess`/`onFailure`; typed error keys matched in ViewModels.
9. Resources: all strings in `strings.xml` (EN+AR), vector drawables `ic_*.xml`, colors only through `MaterialTheme.colorScheme` / `LocalCustomColors`.
10. Reuse `ui/common` components (`AppTopBar`, `AppTextField`, `RoundedButton`, `BottomSheet`, `ErrorModal`, `FullScreenLoaderDialog`, `EmptyState`, ...) before writing new ones.
11. Images with Coil (`AsyncImage`), animations with Lottie Compose where needed, maps with Maps Compose.
12. Click handlers that navigate or submit are wrapped in `rememberClickDelay()` debounce.
13. Version catalog for every dependency; Gradle Kotlin DSL; per-module `build.gradle.kts` following the same template (JVM 11, minSdk 24, compileSdk 35).

## 14. Known Quirks (be aware, don't blindly copy)

- Release build has `isMinifyEnabled = false` (no obfuscation/shrinking configured yet).
- `runBlocking` for initial locale load in `MyApp.onCreate` (intentional trade-off before first frame).
- A Google Maps API key is hardcoded in `AndroidManifest.xml` (`com.google.android.geo.API_KEY`) — for the new app inject it via manifest placeholders/local properties instead.
- Unit/instrumented tests are placeholders only (Example tests); the architecture (use cases, contracts, Koin) was designed to be testable but tests were not written.
- Minor naming inconsistencies exist (a few files start lowercase, e.g., `cartScreen.kt`, `bottomBarRouteDelivery.kt`; some Koin module vals start uppercase, e.g., `SplashModule`). Prefer PascalCase for files and lowerCamelCase for module vals in new code.
- Room and some catalog entries (e.g., `room`, `ktor` versions) exist in the catalog but Room is never applied — don't add it unless needed.
