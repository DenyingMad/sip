package com.devilpanda.sip.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.devilpanda.sip.R;
import com.devilpanda.sip.model.User;
import com.devilpanda.sip.viewmodel.UserViewModel;

import java.util.Locale;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private UserViewModel viewModel;
    private NavController navController;

    private Button remove, add;
    private TextView drankWater, target, left, percent;
    private ProgressBar progressBar;
    private ImageView image;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: viewmodel init");

        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        viewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onViewCreated: checking first launch and init views");

        navController = Navigation.findNavController(view);

        initViews(view);

        checkFirstLaunch();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int viewId = v.getId();
                Bundle arg = new Bundle();
                if (viewId == R.id.home_add_btn) {
                    arg.putBoolean("Change", true);
                }
                else if (viewId == R.id.home_remove_btn) {
                    arg.putBoolean("Change", false);
                }
                Navigation.findNavController(view)
                        .navigate(R.id.action_homeFragment_to_addRemoveFragment, arg);

            }
        };

        add.setOnClickListener(listener);
        remove.setOnClickListener(listener);

    }

    private void initViews(View view) {
        Log.d(TAG, "initViews");
        // Text Fields
        drankWater = view.findViewById(R.id.home_drank_water);
        target = view.findViewById(R.id.home_target_water);
        left = view.findViewById(R.id.home_left_water);
        percent = view.findViewById(R.id.home_percent);
        // Buttons
        remove = view.findViewById(R.id.home_remove_btn);
        add = view.findViewById(R.id.home_add_btn);
        // Other
        progressBar = view.findViewById(R.id.home_circle_progress);
        image = view.findViewById(R.id.home_image);
    }

    private void setupViews(User user) {
        String drankWaterString = String.valueOf(user.getWaterDrankToday()) + " мл";
        drankWater.setText(drankWaterString);
        String targetString = String.valueOf(user.getWaterTotal()) + " мл";
        target.setText(targetString);
        String leftString = String.valueOf(user.getWaterLeft()) + " мл";
        left.setText(leftString);
        String percentString = String.format(Locale.ENGLISH,"%.1f", user.getPercent()) + " %";
        percent.setText(percentString);
        progressBar.setProgress((int) Math.round(user.getPercent()));
    }

    /**
     * Функция проверяет, запущено ли приложение впервые
     * Если в SharedPreferences лежит значение "First" по ключу "IsFirstLaunch",
     * то запускается фрагмент {@link StartFragment}, который начинает цепочку фрагментов для ввода
     * настроек пользователя.
     * {@link ThirdSettingFragment} записывает в SharedPreferences значение "Not First"
     * по ключу "IsFirstLaunch"
     * <p>
     * Если в SharedPreferences лежит значение "Not First",
     * то запускается фрагмент {@link HomeFragment}, используются настройки, полученные при
     * первом запуске, из базы данных загружаются поля пользователя.
     */
    private void checkFirstLaunch() {
        Log.d(TAG, "checkFirstLaunch: checking");
        SharedPreferences sharedPreferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        String isFirstLaunch = sharedPreferences.getString("IsFirstLaunch", "First");
        if (isFirstLaunch.equals("First")) {
            Log.d(TAG, "checkFirstLaunch: first launch, go to start screen");
            navController.navigate(R.id.action_homeFragment_to_startFragment);
        } else if (isFirstLaunch.equals("Not First")) {
            Log.d(TAG, "checkFirstLaunch: not first launch");
            viewModel.getUser().observe(requireActivity(), new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    user.countDailyWaterAmount();
                    user.countWaterLeft();
                    setupViews(user);
                    double p = user.getPercent();
                    if (p < 20 && p != 0) {
                        image.setImageDrawable(getResources().getDrawable(R.drawable.angry_men));
                    } else if (p >= 20 && p < 50) {
                        image.setImageDrawable(getResources().getDrawable(R.drawable.sad_men));
                    } else if (p == 0) {
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ok_man));
                    } else {
                        image.setImageDrawable(getResources().getDrawable(R.drawable.happy_men));
                    }
                }
            });
        }
    }

}