package therabbit.project_test04.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import therabbit.project_test04.R;
import therabbit.project_test04.view.SlidingTabLayout;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class MainFragment extends Fragment {

    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;

    public MainFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        System.out.println("Main FFFFF");
        /*if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);*/
        if (savedInstanceState == null) {
            Fragment newFragment = new Fragment_Box_SMS();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.FF_Box, newFragment).commit();
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here


        /*viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);

        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                switch (position) {
                    case 0:
                        return Fragment_Box_SMS.newInstance();
                    //case 1:
                        //return //Fragment_Generate_Key.newInstance();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "SMS Box";
                    //case 1:
                        //return "Key Pair";
                    default:
                        return null;
                }
            }
        });
        //viewPager.setCurrentItem(0);  /* set page current */
        /*slidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.slidingTabLayout);

        slidingTabLayout.setDistributeEvenly(true);  //แบ่งให้มันเท่าๆกัน

        slidingTabLayout.setViewPager(viewPager);*/
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

}
