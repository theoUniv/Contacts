package com.simplemobiletools.contacts.adapters

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.simplemobiletools.contacts.R
import com.simplemobiletools.contacts.activities.MainActivity
import com.simplemobiletools.contacts.extensions.config
import com.simplemobiletools.contacts.fragments.MyViewPagerFragment
import com.simplemobiletools.contacts.helpers.CONTACTS_TAB_MASK
import com.simplemobiletools.contacts.helpers.FAVORITES_TAB_MASK
import com.simplemobiletools.contacts.helpers.tabsList
import com.simplemobiletools.contacts.models.Contact

class ViewPagerAdapter(val activity: MainActivity, val contacts: ArrayList<Contact>) : PagerAdapter() {
    val showTabs = activity.config.showTabs

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layout = getFragment(position)
        val view = activity.layoutInflater.inflate(layout, container, false)
        container.addView(view)

        (view as MyViewPagerFragment).apply {
            setupFragment(activity)
            refreshContacts(contacts)
        }
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, item: Any) {
        container.removeView(item as View)
    }

    override fun getCount() = tabsList.filter { it and showTabs != 0 }.size

    override fun isViewFromObject(view: View, item: Any) = view == item

    private fun getFragment(position: Int): Int {
        return when (position) {
            0 -> {
                when {
                    showTabs and CONTACTS_TAB_MASK != 0 -> R.layout.fragment_contacts
                    showTabs and FAVORITES_TAB_MASK != 0 -> R.layout.fragment_favorites
                    else -> R.layout.fragment_groups
                }
            }
            1 -> {
                if (showTabs and CONTACTS_TAB_MASK != 0) {
                    if (showTabs and FAVORITES_TAB_MASK != 0) {
                        R.layout.fragment_favorites
                    } else {
                        R.layout.fragment_groups
                    }
                } else {
                    R.layout.fragment_groups
                }
            }
            else -> R.layout.fragment_groups
        }
    }
}
