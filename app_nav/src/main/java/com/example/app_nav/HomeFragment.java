package com.example.app_nav;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.app_nav.model.User;

// Fragment 生命週期
// https://img-blog.csdnimg.cn/img_convert/8998b924006e95802ef00a14f98870aa.png
public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn = view.findViewById(R.id.button);
        btn.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            // 自帶參數
            Bundle bundle = new Bundle(); // 可以想成它是放變數的容器
            bundle.putString("remote_name", "A01");
            bundle.putInt("remote_age", 18);
            User user = new User("B02", 20);
            bundle.putSerializable("user", user);
            // 導航(不帶參數)
            //controller.navigate(R.id.action_homeFragment_to_detailFragment);
            // 導航(帶參數)
            controller.navigate(R.id.action_homeFragment_to_detailFragment, bundle);
        });
    }
}