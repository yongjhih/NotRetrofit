/*
 * Copyright (C) 2015 8tory, Inc.
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.retrofit2.app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.text.TextUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.*;

import com.github.retrofit2.*;

import butterknife.InjectView;
import butterknife.ButterKnife;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private GitHub github;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        github = GitHub.create(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        setupAdapter(adapter);
        viewPager.setAdapter(adapter);
    }

    private void setupAdapter(Adapter adapter) {
        adapter.fragments.add(FragmentPage.create().title("yongjhih").fragment(() -> {
            return RxCardsFragment.create()
                .items(Observable.defer(() -> github.repositories("yongjhih").toSortedList((a, b) -> {
                    return b.stargazers_count - a.stargazers_count;
                }).flatMap(list -> Observable.from(list)).map(repo -> {
                    RxCard card = new RxCard();
                    card.icon = Observable.just(repo.owner.avatar_url);
                    card.text1 = Observable.just(repo.name);
                    card.message = Observable.just(repo.description);
                    card.likeCount = Observable.just(repo.stargazers_count);
                    card.commentCount = Observable.just(repo.forks_count);
                    //card.image = Observable.just(contributor.originalPic());
                    return card;
                })));
        }));
        adapter.fragments.add(FragmentPage.create().title("yongjhih/retrofit2").fragment(() -> {
            return RxCardsFragment.create()
                .items(Observable.defer(() -> github.contributors("yongjhih", "retrofit2").map(contributor -> {
                    RxCard card = new RxCard();
                    card.icon = Observable.just(contributor.avatar_url);
                    card.text1 = Observable.just(contributor.login);
                    card.message = Observable.just(contributor.login);
                    //card.image = Observable.just(contributor.originalPic());
                    return card;
                })));
        }));
        adapter.fragments.add(FragmentPage.create().title("8tory").fragment(() -> {
            return RxCardsFragment.create()
                .items(Observable.defer(() -> github.orgRepositories("8tory").toSortedList((a, b) -> {
                    return b.stargazers_count - a.stargazers_count;
                }).flatMap(list -> Observable.from(list)).map(repo -> {
                    RxCard card = new RxCard();
                    card.icon = Observable.just(repo.owner.avatar_url);
                    card.text1 = Observable.just(repo.name);
                    card.message = Observable.just(repo.description);
                    card.likeCount = Observable.just(repo.stargazers_count);
                    card.commentCount = Observable.just(repo.forks_count);
                    //card.image = Observable.just(contributor.originalPic());
                    return card;
                })));
        }));
        adapter.fragments.add(FragmentPage.create().title("8tory/json2notification").fragment(() -> {
            return RxCardsFragment.create()
                .items(Observable.defer(() -> github.contributors("8tory", "json2notification").map(contributor -> {
                    RxCard card = new RxCard();
                    card.icon = Observable.just(contributor.avatar_url);
                    card.text1 = Observable.just(contributor.login);
                    card.message = Observable.just(contributor.login);
                    //card.image = Observable.just(contributor.originalPic());
                    return card;
                })));
        }));
        adapter.fragments.add(FragmentPage.create().title("yongjhih/RetroFacebook").fragment(() -> {
            return RxCardsFragment.create()
                .items(Observable.defer(() -> github.contributors("yongjhih", "RetroFacebook").map(contributor -> {
                    RxCard card = new RxCard();
                    card.icon = Observable.just(contributor.avatar_url);
                    card.text1 = Observable.just(contributor.login);
                    card.message = Observable.just(contributor.login);
                    //card.image = Observable.just(contributor.originalPic());
                    return card;
                })));
        }));
        adapter.fragments.add(FragmentPage.create().title("yongjhih/proguard-annotations").fragment(() -> {
            return RxCardsFragment.create()
                .items(Observable.defer(() -> github.contributors("yongjhih", "proguard-annotations").map(contributor -> {
                    RxCard card = new RxCard();
                    card.icon = Observable.just(contributor.avatar_url);
                    card.text1 = Observable.just(contributor.login);
                    card.message = Observable.just(contributor.login);
                    //card.image = Observable.just(contributor.originalPic());
                    return card;
                })));
        }));
        adapter.fragments.add(FragmentPage.create().title("yongjhih/RxParse").fragment(() -> {
            return RxCardsFragment.create()
                .items(Observable.defer(() -> github.contributors("yongjhih", "RxParse").map(contributor -> {
                    RxCard card = new RxCard();
                    card.icon = Observable.just(contributor.avatar_url);
                    card.text1 = Observable.just(contributor.login);
                    card.message = Observable.just(contributor.login);
                    //card.image = Observable.just(contributor.originalPic());
                    return card;
                })));
        }));
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    static class FragmentPage {
        Func0<Fragment> onFragment;
        Fragment fragment;
        String title;

        public Fragment fragment() {
            if (fragment == null) fragment = onFragment.call();
            return fragment;
        }

        public String title() {
            return title;
        }

        public FragmentPage fragment(Func0<Fragment> onFragment) {
            this.onFragment = onFragment;
            return this;
        }

        public FragmentPage title(String title) {
            this.title = title;
            return this;
        }

        public static FragmentPage create() {
            return new FragmentPage();
        }

    }

    static class Adapter extends FragmentPagerAdapter {
        public List<FragmentPage> fragments = new ArrayList<>(); // NOTICE: memleak

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position).fragment();
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments.get(position).title();
        }

        @Override
        public int getItemPosition(Object object) {
            return FragmentPagerAdapter.POSITION_NONE;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //github.onActivityResult(requestCode, resultCode, data);
    }
}
