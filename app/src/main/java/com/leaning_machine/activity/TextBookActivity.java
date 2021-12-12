package com.leaning_machine.activity;

import android.os.Bundle;

import com.leaning_machine.R;
import com.leaning_machine.layout.TopWithContentLayout;
import com.leaning_machine.model.App;

import java.util.ArrayList;
import java.util.List;

public class TextBookActivity extends BaseActivity {
    TopWithContentLayout topWithContentLayout;
    List<String> topContents;
    List<Integer> topImgIds;
    List<String> titles;
    List<App> keWenJiangLianList;
    List<App> aiWeiKeList;
    List<App> tongBuHuiBenList;
    List<App> tongBuTiKuList;
    List<App> kaoBaList;
    List<App> kaoShenJunList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        topWithContentLayout = findViewById(R.id.content);
        initData();
    }

    private void initData() {
        topContents = new ArrayList<>();
        topContents.add(getString(R.string.ke_wen_jiang_lian_holder));
        topContents.add(getString(R.string.ai_wei_ke_holder));
        topContents.add(getString(R.string.tong_bu_hui_ben_holder));
        topContents.add(getString(R.string.tong_bu_ti_ku_holder));
        topContents.add(getString(R.string.kao_ba_xi_lie_holder));
        topContents.add(getString(R.string.kao_shen_jun_holder));

        topImgIds = new ArrayList<>();
        topImgIds.add(R.mipmap.ke_wen_jiang_lian_holder);
        topImgIds.add(R.mipmap.ai_wei_ke_holder);
        topImgIds.add(R.mipmap.tong_bu_hui_ben_holder);
        topImgIds.add(R.mipmap.tong_bu_ti_ku_holder);
        topImgIds.add(R.mipmap.kao_ba_holder);
        topImgIds.add(R.mipmap.kao_shen_jun_holder);

        initKeWenJiangLianList();
        initAIWeiKeList();
        initTongBuHuiBen();
        initTongTiKu();
        initKaoBaList();
        initKaoShenJunList();

        titles = new ArrayList<>();
        titles.add(getString(R.string.ke_wen_jiang_lian));
        titles.add(getString(R.string.ai_wei_ke));
        titles.add(getString(R.string.tong_bu_hui_ben));
        titles.add(getString(R.string.tong_bu_ti_ku));
        titles.add(getString(R.string.kao_ba_xi_lie));
        titles.add(getString(R.string.kao_shen_jun_xi_lie));

        topWithContentLayout.setAppList(keWenJiangLianList);

        topWithContentLayout.addTitle(titles, new TopWithContentLayout.TitleClick() {
            @Override
            public void onClick(int index, String title) {
                topWithContentLayout.setTopContent(topContents.get(index), getDrawable(topImgIds.get(index)));
                switch (index) {
                    case 0:
                        topWithContentLayout.setAppList(keWenJiangLianList);
                        break;
                    case 1:
                        topWithContentLayout.setAppList(aiWeiKeList);
                        break;
                    case 2:
                        topWithContentLayout.setAppList(tongBuHuiBenList);
                        break;
                    case 3:
                        topWithContentLayout.setAppList(tongBuTiKuList);
                        break;
                    case 4:
                        topWithContentLayout.setAppList(kaoBaList);
                        break;
                    case 5:
                        topWithContentLayout.setAppList(kaoShenJunList);
                        break;
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_text_book;
    }

    private void initKeWenJiangLianList() {
        keWenJiangLianList = new ArrayList<>();
        keWenJiangLianList.add(new App(R.mipmap.ke_wen_jiang_lian_xiao_xue, getString(R.string.xiao_xue_yu_shu_ying_tong_bu)));
        keWenJiangLianList.add(new App(R.mipmap.ke_wen_jiang_lian_gao_zhong, getString(R.string.chu_gao_zhong_ying_yu_tong_bu)));
    }

    private void initAIWeiKeList() {
        aiWeiKeList = new ArrayList<>();
        aiWeiKeList.add(new App(R.mipmap.ai_wei_ke_shu_li_hua, getString(R.string.zhong_xue_shu_li_hua)));
        aiWeiKeList.add(new App(R.mipmap.ai_wei_ke_quan_wei_lian, getString(R.string.qu_wei_quan_ke_lian)));
    }


    private void initTongBuHuiBen() {
        tongBuHuiBenList = new ArrayList<>();
        tongBuHuiBenList.add(new App(R.mipmap.tong_bu_hui_ben_yi_qi_xue, getString(R.string.yi_qi_xue)));
    }

    private void initTongTiKu() {
        tongBuTiKuList = new ArrayList<>();
        tongBuTiKuList.add(new App(R.mipmap.tong_bu_ti_ku_jing_you, getString(R.string.jing_you_wang)));
        tongBuTiKuList.add(new App(R.mipmap.tong_bu_ti_ku_yuan_ti_ku, getString(R.string.yuan_ti_ku)));
        tongBuTiKuList.add(new App(R.mipmap.tong_bu_ti_ku_mo_fa, getString(R.string.wei_lai_mo_fa_xiao)));
    }

    private void initKaoBaList() {
        kaoBaList = new ArrayList<>();
        kaoBaList.add(new App(R.mipmap.kao_ba_english));
        kaoBaList.add(new App(R.mipmap.kao_ba_yu_wen));
        kaoBaList.add(new App(R.mipmap.kao_ba_math));
        kaoBaList.add(new App(R.mipmap.kao_ba_hua_xue));
        kaoBaList.add(new App(R.mipmap.kao_ba_wu_li));
        kaoBaList.add(new App(R.mipmap.kao_ba_sheng_wu));
        kaoBaList.add(new App(R.mipmap.kao_ba_li_shi));
        kaoBaList.add(new App(R.mipmap.kao_ba_zheng_zhi));
        kaoBaList.add(new App(R.mipmap.kao_ba_di_li));
    }

    private void initKaoShenJunList() {
        kaoShenJunList = new ArrayList<>();
        kaoShenJunList.add(new App(R.mipmap.kao_shen_jun_quan_ce));
        kaoShenJunList.add(new App(R.mipmap.kao_shen_jun_english));
        kaoShenJunList.add(new App(R.mipmap.kao_shen_jun_language));
        kaoShenJunList.add(new App(R.mipmap.kao_shen_jun_math));
        kaoShenJunList.add(new App(R.mipmap.kao_shen_jun_wu_li));
        kaoShenJunList.add(new App(R.mipmap.kao_shen_jun_hua_xue));
        kaoShenJunList.add(new App(R.mipmap.kao_shen_jun_di_li));
        kaoShenJunList.add(new App(R.mipmap.kao_shen_jun_li_shi));
        kaoShenJunList.add(new App(R.mipmap.kao_shen_jun_sheng_wu));
    }


}