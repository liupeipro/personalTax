package com.hh.personaltax;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import com.hh.personaltax.about.AboutFragment;
import com.hh.personaltax.base.BaseFragment;
import com.hh.personaltax.calculator.CalculatorFragment;
import com.hh.personaltax.setting.SetsFragment;
import com.hh.personaltax.view.BottomBar;
import com.hh.personaltax.view.BottomBarTab;
import java.util.ArrayList;
import java.util.List;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends SupportActivity implements BaseFragment.OnBackToFirstListener {
    private List<SupportFragment> mFragments = new ArrayList<SupportFragment>();
    private BottomBar mBottomBar;
    
    @Override public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }
    
    @Override public void onBackToFirstFragment() {
        mBottomBar.setCurrentItem(0);
    }
    
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        SupportFragment firstFrag = findFragment(CalculatorFragment.class);
        
        if (firstFrag == null) {
            //          初始化
            mFragments.add(CalculatorFragment.newInstance());
            mFragments.add(SetsFragment.newInstance());
            mFragments.add(AboutFragment.newInstance());
            loadMultipleRootFragment(R.id.fl_container, 0, mFragments.get(0), mFragments.get(1),
                                     mFragments.get(2));
        } else {
            
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments.set(0, firstFrag);
            mFragments.set(1, findFragment(SetsFragment.class));
            mFragments.set(2, findFragment(AboutFragment.class));
        }
        
        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        mBottomBar.addItem(new BottomBarTab(this, R.mipmap.ic_discover_white_24dp,
                                            getString(R.string.tab_calculator)))
                  .addItem(new BottomBarTab(this, R.mipmap.ic_message_white_24dp,
                                            getString(R.string.tab_set)))
                  .addItem(new BottomBarTab(this, R.mipmap.ic_message_white_24dp,
                                            getString(R.string.tab_about)));
        
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments.get(position), mFragments.get(prePosition));
            }
            
            @Override public void onTabUnselected(int position) {
            }
            
            @Override public void onTabReselected(int position) {
                final SupportFragment currentFragment = mFragments.get(position);
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();
                
                // 如果不在该类别Fragment的主页,则回到主页;
                if (count > 1) {
                    if (currentFragment instanceof CalculatorFragment) {
                        currentFragment.popToChild(CalculatorFragment.class, false);
                    } else if (currentFragment instanceof SetsFragment) {
                        currentFragment.popToChild(SetsFragment.class, false);
                    } else if (currentFragment instanceof AboutFragment) {
                        currentFragment.popToChild(AboutFragment.class, false);
                    }
                    return;
                }
                
                // 这里推荐使用EventBus来实现 -> 解耦
                if (count == 1) {
                    
                    // 在FirstPagerFragment中接收, 因为是嵌套的孙子Fragment 所以用EventBus比较方便
                    // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                    //                  EventBusActivityScope.getDefault(MainActivity.this).post(new TabSelectedEvent(position));
                }
            }
        });
    }
    
    @Override public FragmentAnimator onCreateFragmentAnimator() {
        
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }
}

//~ Formatted by Jindent --- http://www.jindent.com
