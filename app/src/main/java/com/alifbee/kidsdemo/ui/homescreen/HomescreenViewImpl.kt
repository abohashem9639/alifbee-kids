package com.alifbee.kidsdemo.ui.homescreen

import android.graphics.Paint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.core.UiState
import com.alifbee.kidsdemo.data.model.Assignment
import com.alifbee.kidsdemo.data.model.Course
import com.alifbee.kidsdemo.data.model.Feature
import com.alifbee.kidsdemo.data.model.HomescreenData
import com.alifbee.kidsdemo.data.model.PricingPlan
import com.alifbee.kidsdemo.data.model.Service
import com.alifbee.kidsdemo.data.model.StreakStats
import com.alifbee.kidsdemo.data.model.UserProfile
import com.alifbee.kidsdemo.data.model.UserStats
import com.alifbee.kidsdemo.data.model.WeeklyProgress
import com.alifbee.kidsdemo.databinding.FragmentHomescreenBinding
import com.alifbee.kidsdemo.databinding.ItemDayBinding
import com.alifbee.kidsdemo.databinding.ItemFeatureBinding
import com.alifbee.kidsdemo.databinding.ItemPricingBinding
import com.alifbee.kidsdemo.databinding.ItemServiceBinding
import com.alifbee.kidsdemo.ui.common.BaseObservableView
import java.util.Locale

class HomescreenViewImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?,
) : BaseObservableView<HomescreenView.Listener>(), HomescreenView {

    private var _binding: FragmentHomescreenBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = FragmentHomescreenBinding.inflate(inflater, parent, false)
        rootView = binding.root

        ViewCompat.setOnApplyWindowInsetsListener(binding.homescreenContent) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = bars.top, bottom = bars.bottom)
            insets
        }
    }

    override fun render(state: UiState<HomescreenData>) {
        if (state is UiState.Success) {
            renderProfile(state.data.profile, state.data.stats)
            renderCurrentCourse(state.data.currentCourse)
            renderFeatures(state.data.features)
            renderServices(state.data.services)
            renderStatistic(state.data.weeklyProgress, state.data.streakStats)
            renderAssignment(state.data.currentAssignment)
            renderPricing(state.data.pricingPlans)
        }
    }

    private fun renderProfile(profile: UserProfile, stats: UserStats) {
        val profileSection = binding.sectionProfile
        val rootContext = rootContext

        profileSection.greeting.text = rootContext.getString(R.string.greeting_hi, profile.name)
        profileSection.email.text = profile.email
        profileSection.premiumRow.visibility = if (profile.isPremium) {
            android.view.View.VISIBLE
        } else {
            android.view.View.GONE
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
        val items: List<ItemFeatureBinding> = with(binding.sectionFeatures) {
            listOf(feature0, feature1, feature2)
        }
        items.forEachIndexed { index, item ->
            val feature = features.getOrNull(index)
            item.root.isVisible = feature != null
            if (feature != null) {
                item.featureIcon.setImageResource(feature.iconRes)
                item.featureLabel.text = feature.name
            }
        }
    }

    private fun renderServices(services: List<Service>) {
        val items: List<ItemServiceBinding> = with(binding.sectionServices) {
            listOf(service0, service1, service2)
        }
        items.forEachIndexed { index, item ->
            val service = services.getOrNull(index)
            item.root.isVisible = service != null
            if (service != null) {
                item.serviceIcon.setImageResource(service.iconRes)
                item.serviceLabel.text = service.name
                item.root.setCardBackgroundColor(
                    ContextCompat.getColor(rootContext, service.backgroundColorRes)
                )
            }
        }
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
                if (day.completed) R.drawable.bg_day_completed else R.drawable.bg_day_empty
            )
            cell.dayLightning.isVisible = day.completed
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
        section.assignmentProgress.progress = assignment.progressPercent
        section.assignmentProgressText.text =
            rootContext.getString(R.string.percent_format, assignment.progressPercent)
        section.assignmentTitle.text = assignment.title
        section.assignmentType.text = assignment.type
        section.assignmentDueDate.text = assignment.dueDate
        section.assignmentClassroom.text = assignment.classroom
        section.assignmentLessons.text = assignment.lessonsProgress
        section.assignmentLevel.text = assignment.level
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

        item.bestValueBadge.isVisible = plan.isBestValue

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
        binding.sectionProfile.profileCard.setOnClickListener { action() }
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

    override fun destroyView() {
        _binding = null
    }
}
