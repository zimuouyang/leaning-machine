package com.leaning_machine.activity;

import android.os.Bundle;

import com.leaning_machine.R;
import com.leaning_machine.layout.TopWithContentLayout;
import com.leaning_machine.model.App;

import java.util.ArrayList;
import java.util.List;

public class EnglishActivity extends BaseActivity {
    TopWithContentLayout topWithContentLayout;
    List<App> pinDuList;
    List<App> moErDuoList;
    List<App> aiYueDuList;
    List<App> qinLianXiList;
    List<App> beiDanCiList;
    List<App> quYiZhiList;
    List<String> titles;
    List<String> topContents;
    List<Integer> topImgIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public void initView() {
        topWithContentLayout = findViewById(R.id.content);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_english;
    }


    private void initData() {
        topContents = new ArrayList<>();
        topContents.add(getString(R.string.xue_pin_du_holder_content));
        topContents.add(getString(R.string.mo_er_duo_holder_content));
        topContents.add(getString(R.string.ai_yue_du_holder_content));
        topContents.add(getString(R.string.qin_lian_xi_holder_content));
        topContents.add(getString(R.string.bei_dan_ci_holder_content));
        topContents.add(getString(R.string.qu_yi_zhi_holder_content));

        topImgIds = new ArrayList<>();
        topImgIds.add(R.mipmap.english_pin_du_holder);
        topImgIds.add(R.mipmap.english_mo_er_duo_holder);
        topImgIds.add(R.mipmap.english_ai_yue_du_holder);
        topImgIds.add(R.mipmap.english_qin_lian_xi_holder);
        topImgIds.add(R.mipmap.english_bei_dan_ci_holder);
        topImgIds.add(R.mipmap.english_qu_yi_zhi_holder);

        initPinDuList();
        initMoErDuoList();
        initAiYueDuList();
        initQinLianXiList();
        initBeiDanCiList();
        initQiYiZhiList();


        topWithContentLayout.setAppList(pinDuList);


        titles = new ArrayList<>();
        titles.add(getString(R.string.pin_du));
        titles.add(getString(R.string.mo_er_duo));
        titles.add(getString(R.string.ai_yue_du));
        titles.add(getString(R.string.qin_lian_xi));
        titles.add(getString(R.string.bei_dan_ci));
        titles.add(getString(R.string.qi_yi_zhi));
        topWithContentLayout.addTitle(titles, new TopWithContentLayout.TitleClick() {
            @Override
            public void onClick(int index, String title) {
                topWithContentLayout.setTopContent(topContents.get(index), getDrawable(topImgIds.get(index)));
                switch (index) {
                    case 0:
                        topWithContentLayout.setAppList(pinDuList);
                        break;
                    case 1:
                        topWithContentLayout.setAppList(moErDuoList);
                        break;
                    case 2:
                        topWithContentLayout.setAppList(aiYueDuList);
                        break;
                    case 3:
                        topWithContentLayout.setAppList(qinLianXiList);
                        break;
                    case 4:
                        topWithContentLayout.setAppList(beiDanCiList);
                        break;
                    case 5:
                        topWithContentLayout.setAppList(quYiZhiList);
                        break;
                }
            }
        });
    }

    private void initPinDuList() {
        pinDuList = new ArrayList<>();
        pinDuList.add(new App(R.mipmap.english_speed_phonics, getString(R.string.speed_phonics)));
        pinDuList.add(new App(R.mipmap.english_abc_kids, getString(R.string.abc_kids)));
        pinDuList.add(new App( R.mipmap.english_starfall_abcs, getString(R.string.starfall_abcs)));
        pinDuList.add(new App(R.mipmap.english_starfall_learn, getString(R.string.starfall_learn_to_read)));
        pinDuList.add(new App(R.mipmap.english_kids_sight_words, getString(R.string.kids_sight_words)));
        pinDuList.add(new App(R.mipmap.english_play_sight_words, getString(R.string.play_sight_words)));
        pinDuList.add(new App(R.mipmap.english_sight_words, getString(R.string.sight_words)));
        pinDuList.add(new App(R.mipmap.english_abc_spelling, getString(R.string.abc_spelling)));
    }

    private void initMoErDuoList() {
        moErDuoList = new ArrayList<>();
        moErDuoList.add(new App(R.mipmap.english_khan_kids, getString(R.string.khan_kids)));
        moErDuoList.add(new App(R.mipmap.english_math_kids, getString(R.string.math_kids)));
        moErDuoList.add(new App(R.mipmap.english_hui_ben, getString(R.string.dong_ting_hui_ben)));
        moErDuoList.add(new App(R.mipmap.english_ying_yu_qi_meng, getString(R.string.hai_ni_han_english)));
        moErDuoList.add(new App(R.mipmap.english_raz_ke_tang, getString(R.string.raz_ke_tang)));
    }

    private void initAiYueDuList() {
        aiYueDuList = new ArrayList<>();
        aiYueDuList.add(new App(R.mipmap.english_read_along, getString(R.string.read_along)));
        aiYueDuList.add(new App(R.mipmap.english_reading_pro, getString(R.string.reading_pro)));
        aiYueDuList.add(new App(R.mipmap.english_abc_reading, getString(R.string.abc_reading)));
        aiYueDuList.add(new App(R.mipmap.english_bai_ci_zhan_yue_du, getString(R.string.bai_ci_zhan_yue_du)));
        aiYueDuList.add(new App(R.mipmap.english_ko_yu_machine, getString(R.string.kou_yu_machine)));
    }

    private void initQinLianXiList() {
        qinLianXiList = new ArrayList<>();
        qinLianXiList.add(new App(R.mipmap.english_duo_lin_go, getString(R.string.duo_lin_guo)));
        qinLianXiList.add(new App(R.mipmap.english_ef_hello, getString(R.string.ef_hello)));
        qinLianXiList.add(new App(R.mipmap.english_xin_gai_nian, getString(R.string.xin_gai_nian_quan_ce)));
        qinLianXiList.add(new App(R.mipmap.english_civa_machine, getString(R.string.civa_machine)));
    }

    private void initBeiDanCiList() {
        beiDanCiList = new ArrayList<>();
        beiDanCiList.add(new App(R.mipmap.english_bei_dan_ci, getString(R.string.bei_dan_ci)));
        beiDanCiList.add(new App(R.mipmap.english_bai_ci_zhan, getString(R.string.bai_ci_zhan)));
        beiDanCiList.add(new App(R.mipmap.english_3d_dan_ci, getString(R.string.three_dan_ci)));
    }

    private void initQiYiZhiList() {
        quYiZhiList = new ArrayList<>();
        quYiZhiList.add(new App(R.mipmap.english_puzzle_kids, getString(R.string.puzzle_kids)));
        quYiZhiList.add(new App(R.mipmap.english_play_games, getString(R.string.play_games)));
        quYiZhiList.add(new App(R.mipmap.english_molly, getString(R.string.molly)));
        quYiZhiList.add(new App(R.mipmap.english_play_learn, getString(R.string.play_learn_science)));
        quYiZhiList.add(new App(R.mipmap.english_play_learn_engineering, getString(R.string.play_learn_engineering)));
    }

}