package com.alifbee.kidsdemo.ui.homescreen

import android.animation.ObjectAnimator
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.core.widget.NestedScrollView
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.core.UiState
import com.alifbee.kidsdemo.data.model.Assignment
import com.alifbee.kidsdemo.data.model.AssignmentStatus
import com.alifbee.kidsdemo.data.model.Course
import com.alifbee.kidsdemo.data.model.DayState
import com.alifbee.kidsdemo.data.model.Feature
import com.alifbee.kidsdemo.data.model.HomescreenData
import com.alifbee.kidsdemo.data.model.PricingPlan
import com.alifbee.kidsdemo.data.model.Service
import com.alifbee.kidsdemo.data.model.StreakStats
import com.alifbee.kidsdemo.data.model.UserProfile
import com.alifbee.kidsdemo.data.model.UserStats
import com.alifbee.kidsdemo.data.model.WeeklyProgress
import com.alifbee.kidsdemo.databinding.FragmentHomescreenBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alifbee.kidsdemo.databinding.ItemDayBinding
import com.alifbee.kidsdemo.databinding.ItemPricingBinding
import com.alifbee.kidsdemo.ui.common.BaseObservableView
import com.alifbee.kidsdemo.ui.components.FeaturesAdapter
import com.alifbee.kidsdemo.ui.components.ServicesAdapter
import java.util.Locale

class HomescreenViewImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?,
) : BaseObservableView<HomescreenView.Listener>(), HomescreenView {

    private var _binding: FragmentHomescreenBinding? = null
    private val binding get() = _binding!!

    private val featuresAdapter = FeaturesAdapter()
    private val servicesAdapter = ServicesAdapter()

    private var assignmentStatusAction: (() -> Unit)? = null

    private var assignmentTargetProgress: Int = 0
    private var assignmentProgressAnimated: Boolean = false
    private var assignmentAnimator: ObjectAnimator? = null

    init {
        _binding = FragmentHomescreenBinding.inflate(inflater, parent, false)
        rootView = binding.root

        ViewCompat.setOnApplyWindowInsetsListener(binding.homescreenContent) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = bars.top, bottom = bars.bottom)
            insets
        }

        binding.homescreenScroll.alpha = 0f
        binding.homescreenScroll.isVisible = false

        binding.sectionFeatures.featuresList.apply {
            layoutManager = LinearLayoutManager(rootContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = featuresAdapter
        }

        binding.sectionServices.servicesList.apply {
            layoutManager = GridLayoutManager(rootContext, 3)
            adapter = servicesAdapter
        }

        binding.homescreenScroll.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { _, _, _, _, _ ->
                maybeAnimateAssignmentProgress()
            }
        )
    }

    override fun render(state: UiState<HomescreenData>) {
        when (state) {
            UiState.Loading -> showLoading()
            is UiState.Success -> {
                renderProfile(state.data.profile, state.data.stats)
                renderCurrentCourse(state.data.currentCourse)
                renderFeatures(state.data.features)
                renderServices(state.data.services)
                renderStatistic(state.data.weeklyProgress, state.data.streakStats)
                renderAssignment(state.data.currentAssignment)
                renderPricing(state.data.pricingPlans)
                hideLoading()
            }
            is UiState.Error -> hideLoading()
        }
    }

    private fun maybeAnimateAssignmentProgress() {
        if (assignmentProgressAnimated) return
        val view = _binding?.sectionAssignment?.assignmentProgress ?: return
        val rect = Rect()
        val visible = view.getGlobalVisibleRect(rect) && rect.height() > 0
        if (!visible) return
        assignmentProgressAnimated = true
        runAssignmentProgressAnimation(assignmentTargetProgress)
    }

    private fun runAssignmentProgressAnimation(target: Int) {
        val section = _binding?.sectionAssignment ?: return
        assignmentAnimator?.cancel()
            assignmentAnimator = ObjectAnimator.ofInt(section.assignmentProgress, "progress", 0, target).apply {
            duration = ASSIGNMENT_ANIM_DURATION_MS
            interpolator = DecelerateInterpolator()
            addUpdateListener { anim ->
                val value = anim.animatedValue as Int
                section.assignmentProgressText.text =
                    rootContext.getString(R.string.percent_format, value)
            }
            start()
        }
    }

    private fun showLoading() {
        binding.homescreenShimmer.isVisible = true
        binding.homescreenShimmer.alpha = 1f
        binding.homescreenShimmer.startShimmer()
        binding.homescreenScroll.isVisible = false
        binding.homescreenScroll.alpha = 0f
    }

    private fun hideLoading() {
        val shimmer = binding.homescreenShimmer
        val content = binding.homescreenScroll

        if (!shimmer.isVisible) return

        content.alpha = 0f
        content.isVisible = true
        content.animate()
            .alpha(1f)
            .setDuration(FADE_DURATION_MS)
            .start()

        shimmer.animate()
            .alpha(0f)
            .setDuration(FADE_DURATION_MS)
            .withEndAction {
                shimmer.stopShimmer()
                shimmer.isVisible = false
            }
            .start()
    }

    private fun renderProfile(profile: UserProfile, stats: UserStats) {
        val profileSection = binding.sectionProfile
        val rootContext = rootContext

        profileSection.greeting.text = rootContext.getString(R.string.greeting_hi, profile.name)
        profileSection.email.text = profile.email
        profileSection.premiumRow.visibility = if (profile.isPremium) {
            View.VISIBLE
        } else {
            View.GONE
        }

        profileSection.statHoney.statValue.text = stats.honey.toString()
        profileSection.statHoney.statLabel.text = rootContext.getString(R.string.stat_honey)

        profileSection.statStreak.statValue.text = stats.dailyStreak.toString()
        profileSection.statStreak.statLabel.text = rootContext.getString(R.string.stat_daily_streak)

        profileSection.statLesson.statValue.text = stats.lessons.toString()
        profileSection.statLesson.statLabel.text = rootContext.getString(R.string.stat_lesson)

        profileSection.statWords.statValue.text = stats.words.toString()
        profileSection.statWords.statLabel.text = rootContext.getString(R.string.stat_words)
    }

    private fun renderCurrentCourse(course: Course?) {
        val courseSection = binding.sectionCurrentCourse
        courseSection.currentCourseSection.isVisible = course != null
        if (course == null) return
        courseSection.courseTitle.text = course.title
        courseSection.courseLevel.text = course.level
        courseSection.courseProgress.progress = course.progressPercent
    }

    private fun renderFeatures(features: List<Feature>) {
        featuresAdapter.submitList(features)
    }

    private fun renderServices(services: List<Service>) {
        servicesAdapter.submitList(services)
    }

    private fun renderStatistic(weekly: WeeklyProgress, streak: StreakStats) {
        val statistic = binding.sectionStatistic
        statistic.streakBadge.text = streak.currentStreak.toString()
        statistic.activeDaysValue.text = streak.activeDays.toString()
        statistic.longestStreakValue.text = streak.longestStreak.toString()

        val cells: List<ItemDayBinding> = with(statistic) {
            listOf(day0, day1, day2, day3, day4, day5, day6)
        }
        cells.forEachIndexed { index, cell ->
            val day = weekly.days.getOrNull(index)
            cell.dayCell.isVisible = day != null
            if (day == null) return@forEachIndexed

            cell.dayLabel.text = day.label
            cell.dayLabel.setTypeface(null, if (day.isCurrent) Typeface.BOLD else Typeface.NORMAL)
            cell.dayIndicator.setBackgroundResource(
                when (day.state) {
                    DayState.COMPLETED -> R.drawable.bg_day_completed
                    DayState.MISSED -> R.drawable.bg_day_missed
                    DayState.PENDING -> R.drawable.bg_day_empty
                }
            )
            cell.dayLightning.isVisible = day.state != DayState.PENDING
            cell.dayHighlight.background = if (day.isCurrent) {
                ContextCompat.getDrawable(rootContext, R.drawable.bg_day_current)
            } else {
                null
            }
        }
    }

    private fun renderAssignment(assignment: Assignment?) {
        val section = binding.sectionAssignment
        section.assignmentSection.isVisible = assignment != null
        if (assignment == null) return

        assignmentTargetProgress = assignment.progressPercent
        assignmentProgressAnimated = false
        assignmentAnimator?.cancel()
        section.assignmentProgress.progress = 0
        section.assignmentProgressText.text =
            rootContext.getString(R.string.percent_format, 0)
        section.assignmentProgress.post { maybeAnimateAssignmentProgress() }

        section.assignmentTitle.text = assignment.title
        section.assignmentType.text = assignment.type
        section.assignmentDueDate.text = assignment.dueDate
        section.assignmentClassroom.text = assignment.classroom
        section.assignmentLessons.text = assignment.lessonsProgress
        section.assignmentLevel.text = assignment.level

        val (statusBg, statusText, ringColor) = when (assignment.status) {
            AssignmentStatus.TO_DO -> Triple(
                R.drawable.bg_status_todo,
                R.string.status_to_do,
                R.color.assignment_progress,
            )
            AssignmentStatus.DONE -> Triple(
                R.drawable.bg_status_done,
                R.string.status_done,
                R.color.assignment_progress_done,
            )
            AssignmentStatus.OVERDUE -> Triple(
                R.drawable.bg_status_overdue,
                R.string.status_overdue,
                R.color.assignment_progress_overdue,
            )
        }

        section.assignmentStatus.setBackgroundResource(statusBg)
        section.assignmentStatus.setText(statusText)
        section.assignmentProgress.setIndicatorColor(
            ContextCompat.getColor(rootContext, ringColor)
        )

        if (assignment.status == AssignmentStatus.TO_DO) {
            section.assignmentStatus.isClickable = true
            section.assignmentStatus.isFocusable = true
            section.assignmentStatus.setOnClickListener { assignmentStatusAction?.invoke() }
        } else {
            section.assignmentStatus.setOnClickListener(null)
            section.assignmentStatus.isClickable = false
            section.assignmentStatus.isFocusable = false
        }
    }

    private fun renderPricing(plans: List<PricingPlan>) {
        val items: List<ItemPricingBinding> = with(binding.sectionPricing) {
            listOf(plan0, plan1, plan2)
        }
        items.forEachIndexed { index, item ->
            val plan = plans.getOrNull(index)
            item.root.isVisible = plan != null
            if (plan == null) return@forEachIndexed
            bindPricingPlan(item, plan)
        }
    }

    private fun bindPricingPlan(item: ItemPricingBinding, plan: PricingPlan) {
        item.planMonths.text = plan.months.toString()
        item.planMonthsLabel.text = rootContext.getString(
            if (plan.months == 1) R.string.pricing_month else R.string.pricing_months
        )

        item.discountBadge.visibility = if (plan.discountPercent != null) {
            item.discountBadge.text =
                rootContext.getString(R.string.discount_format, plan.discountPercent)
            View.VISIBLE
        } else {
            View.INVISIBLE
        }

        if (plan.originalPriceUsd != null) {
            item.planOriginalPrice.isVisible = true
            item.planOriginalPrice.text = formatPrice(plan.originalPriceUsd)
            item.planOriginalPrice.paintFlags =
                item.planOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            item.planOriginalPrice.isVisible = false
        }

        item.planPrice.text = formatPrice(plan.priceUsd)

        if (plan.months > 1) {
            item.planMonthlyPrice.isVisible = true
            val monthly = plan.priceUsd / plan.months
            item.planMonthlyPrice.text =
                rootContext.getString(R.string.per_month_format, formatPrice(monthly))
        } else {
            item.planMonthlyPrice.isVisible = false
        }

        item.bestValueBadge.visibility = if (plan.isBestValue) View.VISIBLE else View.INVISIBLE

        item.pricingCard.setBackgroundResource(
            when {
                plan.isBestValue -> R.drawable.bg_pricing_card_lavender_flat_bottom
                plan.discountPercent != null -> R.drawable.bg_pricing_card_lavender
                else -> R.drawable.bg_pricing_card_gray
            }
        )
    }

    private fun formatPrice(value: Double): String {
        val formatted = if (value % 1.0 == 0.0) {
            value.toInt().toString()
        } else {
            String.format(Locale.US, "%.2f", value)
        }
        return "$$formatted"
    }

    override fun onSettingsClick(action: () -> Unit) {
        binding.sectionTopBar.btnSettings.setOnClickListener { action() }
    }

    override fun onProfileClick(action: () -> Unit) {
        binding.sectionProfile.profileInfoGroup.setOnClickListener { action() }
    }

    override fun onGoPremiumClick(action: () -> Unit) {
        binding.sectionGoPremium.goPremiumCard.setOnClickListener { action() }
    }

    override fun onCurrentCourseClick(action: () -> Unit) {
        binding.sectionCurrentCourse.courseCard.setOnClickListener { action() }
    }

    override fun onSeeAllCoursesClick(action: () -> Unit) {
        binding.sectionCurrentCourse.seeAllGroup.setOnClickListener { action() }
    }

    override fun onAssignmentClick(action: () -> Unit) {
        binding.sectionAssignment.assignmentSection.setOnClickListener { action() }
    }

    override fun onAssignmentStatusClick(action: () -> Unit) {
        assignmentStatusAction = action
    }

    override fun destroyView() {
        assignmentAnimator?.cancel()
        assignmentAnimator = null
        _binding?.homescreenShimmer?.stopShimmer()
        _binding?.sectionFeatures?.featuresList?.adapter = null
        _binding?.sectionServices?.servicesList?.adapter = null
        _binding = null
    }

    companion object {
        private const val FADE_DURATION_MS = 300L
        private const val ASSIGNMENT_ANIM_DURATION_MS = 1500L
    }
}
