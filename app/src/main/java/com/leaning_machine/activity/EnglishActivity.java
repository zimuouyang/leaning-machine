package com.leaning_machine.activity;

import android.content.Intent;
import android.os.Bundle;

import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.base.dto.BaseDto;
import com.leaning_machine.base.dto.TerminalSign;
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.domain.DefaultObserver;
import com.leaning_machine.layout.PasswordDialog;
import com.leaning_machine.layout.TopWithContentLayout;
import com.leaning_machine.model.App;
import com.leaning_machine.model.VoiceType;
import com.leaning_machine.utils.SharedPreferencesUtils;
import com.leaning_machine.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    @Override
    protected void onResume() {
        super.onResume();
        getLatestSign();
    }

    private void getLatestSign() {
        CommonApiService.instance.getLatestSign().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<BaseDto<TerminalSign>>() {
            @Override
            public void onNext(BaseDto<TerminalSign> terminalSignBaseDto) {
                super.onNext(terminalSignBaseDto);
                if (terminalSignBaseDto.getBusinessCode() == 200) {
                    SharedPreferencesUtils.putString(getApplicationContext(), Constant.TERMINAL_PWD, terminalSignBaseDto.getResult().getSign());
                } else if (terminalSignBaseDto.getBusinessCode() == Constant.INVALID_CODE) {
                    Utils.goToLogin(getApplicationContext());
                }
            }
        });
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

        initTopWithContentLayout();
    }

    private void initTopWithContentLayout() {
        topWithContentLayout.addTitle(titles, new TopWithContentLayout.TitleClick() {
            @Override
            public void onClick(int index, String title) {
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
                        checkPermission();
                        break;
                }
                if (index != 5) {
                    topWithContentLayout.setTopContent(topContents.get(index), getDrawable(topImgIds.get(index)));
                }
            }
        });

        topWithContentLayout.setFinishClick(EnglishActivity.this::finish);
    }



    private void checkPermission() {
        if (SharedPreferencesUtils.getInt(this, Constant.ROLE, 0) == 1) {
            topWithContentLayout.setAppList(quYiZhiList);
            topWithContentLayout.setTopContent(topContents.get(5), getDrawable(topImgIds.get(5)));
        } else {
            PasswordDialog passwordDialog = new PasswordDialog.Builder().context(this).type(VoiceType.PASSWORD).build();
            passwordDialog.setPasswordClick(new PasswordDialog.PasswordClick() {
                @Override
                public void onSuccess() {
                    topWithContentLayout.setAppList(quYiZhiList);
                    topWithContentLayout.setTopContent(topContents.get(5), getDrawable(topImgIds.get(5)));
                }
            });
            passwordDialog.show();
        }
    }

    private void initPinDuList() {
        pinDuList = new ArrayList<>();
        pinDuList.add(new App(R.mipmap.english_speed_phonics, getString(R.string.speed_phonics), getString(R.string.package_name_speed_phonics), getString(R.string.speed_phonics_des)));
        pinDuList.add(new App(R.mipmap.english_abc_kids, getString(R.string.abc_kids), getString(R.string.package_name_abc_kids_phonics), getString(R.string.abc_kids_des)));
        pinDuList.add(new App( R.mipmap.english_starfall_abcs, getString(R.string.starfall_abcs), getString(R.string.package_name_startfall_abcs), getString(R.string.starfall_abcs_des)));
        pinDuList.add(new App(R.mipmap.english_starfall_learn, getString(R.string.starfall_learn_to_read), getString(R.string.package_name_startfall_learn_read), getString(R.string.starfall_learn_to_read_des)));
        pinDuList.add(new App(R.mipmap.english_kids_sight_words, getString(R.string.kids_sight_words), getString(R.string.package_name_kids_sight_words), getString(R.string.kids_sight_words_des)));
        pinDuList.add(new App(R.mipmap.english_play_sight_words, getString(R.string.play_sight_words), getString(R.string.package_name_play_sight_words), getString(R.string.sight_words_hei_ban_des)));
        pinDuList.add(new App(R.mipmap.english_sight_words, getString(R.string.sight_words), getString(R.string.package_name_sight_words), getString(R.string.sight_words_des)));
        pinDuList.add(new App(R.mipmap.english_abc_spelling, getString(R.string.abc_spelling), getString(R.string.package_name_abc_spelling), getString(R.string.abc_spelling_des)));
    }

    private void initMoErDuoList() {
        moErDuoList = new ArrayList<>();
        moErDuoList.add(new App(R.mipmap.english_khan_kids, getString(R.string.khan_kids), getString(R.string.package_name_khan_kids), getString(R.string.khan_kids_des)));
        moErDuoList.add(new App(R.mipmap.english_math_kids, getString(R.string.math_kids), getString(R.string.package_name_math_kids), getString(R.string.math_kids_des)));
        moErDuoList.add(new App(R.mipmap.english_hui_ben, getString(R.string.dong_ting_hui_ben), getString(R.string.package_name_dong_ting_hui_ben), getString(R.string.dong_ting_hui_ben_des)));
        moErDuoList.add(new App(R.mipmap.english_ying_yu_qi_meng, getString(R.string.hai_ni_han_english), getString(R.string.package_name_hai_ni_man_english), getString(R.string.hai_ni_man_english_des)));
        moErDuoList.add(new App(R.mipmap.english_raz_ke_tang, getString(R.string.raz_ke_tang), getString(R.string.package_name_raz), getString(R.string.raz_des)));
    }

    private void initAiYueDuList() {
        aiYueDuList = new ArrayList<>();
        aiYueDuList.add(new App(R.mipmap.english_read_along, getString(R.string.read_along), getString(R.string.package_name_read_long), getString(R.string.read_along_des)));
        aiYueDuList.add(new App(R.mipmap.english_reading_pro, getString(R.string.reading_pro), getString(R.string.package_name_reading_pro), getString(R.string.reading_pro_des)));
        aiYueDuList.add(new App(R.mipmap.english_abc_reading, getString(R.string.abc_reading), getString(R.string.package_name_abc_reading), getString(R.string.abc_reading_des)));
        aiYueDuList.add(new App(R.mipmap.english_bai_ci_zhan_yue_du, getString(R.string.bai_ci_zhan_yue_du), getString(R.string.package_name_bai_ci_zhan_ai_yue_du), getString(R.string.bai_ci_zhan_read_des)));
        aiYueDuList.add(new App(R.mipmap.english_ko_yu_machine, getString(R.string.kou_yu_machine), getString(R.string.package_name_ko_yu_machine)));
    }

    private void initQinLianXiList() {
        qinLianXiList = new ArrayList<>();
        qinLianXiList.add(new App(R.mipmap.english_duo_lin_go, getString(R.string.duo_lin_guo), getString(R.string.package_name_duo_lin_guo), getString(R.string.duo_lin_guo_des)));
        qinLianXiList.add(new App(R.mipmap.english_ef_hello, getString(R.string.ef_hello), getString(R.string.package_name_ef_hello), getString(R.string.ef_hello_des)));
        qinLianXiList.add(new App(R.mipmap.english_xin_gai_nian, getString(R.string.xin_gai_nian_quan_ce), getString(R.string.package_name_xin_gai_nian_quan_ce)));
        qinLianXiList.add(new App(R.mipmap.english_civa_machine, getString(R.string.civa_machine), getString(R.string.package_name_civia_machine)));
    }

    private void initBeiDanCiList() {
        beiDanCiList = new ArrayList<>();
        beiDanCiList.add(new App(R.mipmap.english_bei_dan_ci, getString(R.string.bei_dan_ci), getString(R.string.package_name_ai_bei_dan_ci), getString(R.string.english_ci_dian)));
        beiDanCiList.add(new App(R.mipmap.english_bai_ci_zhan, getString(R.string.bai_ci_zhan), getString(R.string.package_name_bai_ci_zhan), getString(R.string.bai_ci_zhan_des)));
        beiDanCiList.add(new App(R.mipmap.english_3d_dan_ci, getString(R.string.three_dan_ci), getString(R.string.package_name_three_dan_ci), getString(R.string.ai_dan_ci_des)));
    }

    private void initQiYiZhiList() {
        quYiZhiList = new ArrayList<>();
        quYiZhiList.add(new App(R.mipmap.english_puzzle_kids, getString(R.string.puzzle_kids), getString(R.string.package_name_puzzle_kids)));
        quYiZhiList.add(new App(R.mipmap.english_play_games, getString(R.string.play_games), getString(R.string.package_name_play_games)));
        quYiZhiList.add(new App(R.mipmap.english_molly, getString(R.string.molly), getString(R.string.package_name_molly)));
        quYiZhiList.add(new App(R.mipmap.english_play_learn, getString(R.string.play_learn_science), getString(R.string.package_name_play_science)));
        quYiZhiList.add(new App(R.mipmap.english_play_learn_engineering, getString(R.string.play_learn_engineering), getString(R.string.package_name_play_engineering)));
    }

}