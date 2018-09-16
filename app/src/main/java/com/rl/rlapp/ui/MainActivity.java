package com.rl.rlapp.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.rl.rlapp.R;
import com.rl.rlapp.ui.fragments.FirstFragment;
import com.rl.rlapp.ui.fragments.SecondFragment;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;

    /*
    2. Selecting	a	feed	(a	table	item)	will	push	a	new	view	with	the	description	of	the	feed.
    3. The	application	should	now	present	in	the	empty	label	of	the	first	tab (remember?),	the	title
    from	the	feed	that	was	selected	in	the	second	tab.
    4. The	application	should	check	each	RSS	source	(there	are	3	RSS	sources)	every	5	seconds	for	an
    update and	the UI	should	be	updated	immediately	as	soon	as	one	of	the	RSS	sources	provided
    updated	information.
    Note:	You	need	to	take	into	account	the	fact	that	the	different	RSS’s	might	have	different
    response	times.
    5. Whenever	the	application	checks	for	update,	it	should	show	an activity	indicator	that	does	not
    block	the	screen or	the	user	from	interacting	with	the	application.
    6. Try	to	keep	to	good	design	and	documentation	standards	as	best	as	you	can.
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0 : {
                    return FirstFragment.getInstance();
                }
                default : {
                    return SecondFragment.getInstance();
                }
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
