package com.hanjinliang.androidstudy.javabase.fanxing;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.javabase.BeanToMap;
import com.hanjinliang.androidstudy.javabase.JSONTool;
import com.hanjinliang.androidstudy.javabase.StudentBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 泛型学习
 */
public class FanxingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fanxing);
        Button button=new Button(this);
        TextView textView=new TextView(this);
        View view=new View(this);

        Point<? super TextView>  point=new Point<View>(view,view);

        point.setX(button);
        LogUtils.e(point.getX());

        IntegerComparable small= ComparaleUtil.getMinest(new IntegerComparable(1,2),new IntegerComparable(34,2),new IntegerComparable(45,2),new IntegerComparable(16,2),new IntegerComparable(10,2));
        LogUtils.e(small.getX());


        StudentBean studentBean=new StudentBean(20,"刘文豪","男","池河镇");
        studentBean.setName(null);
        Map<String, Object> objectMap = new BeanToMap().beanToMap(studentBean);
        objectMap.toString();

        //json格式化显示
        TextView tv_json=findViewById(R.id.tv_json);
        tv_json.setText(JSONTool.stringToJSON(json));
        try {
            tv_json.setText(new JSONObject(json).toString(5));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<String> strings = letterCombinations("23");
        LogUtils.e(strings);

    }

    String json="{\"code\":200,\"msg\":\"success\",\"data\":{\"topBannerList\":[{\"url\":\"https://www.kongzhongjr.com/wap/activity/notic.html?id=551\",\"title\":\"稳赢宝加息\",\"img\":\"//jrpubimg.kongzhongjr.com/banner_img/20180706065451_750x300.jpg\",\"loginEnterType\":false},{\"url\":\"https://www.kongzhongjr.com/act_wap/activity/cool-summer/\",\"title\":\"高温津贴\",\"img\":\"//jrpubimg.kongzhongjr.com/banner_img/20180629061359_750x300.jpg\",\"loginEnterType\":false},{\"url\":\"https://www.kongzhongjr.com/wap/auto/introduction.html\",\"title\":\"智能投顾功能\",\"img\":\"//jrpubimg.kongzhongjr.com/banner_img/20180604112122_750x300.jpg\",\"loginEnterType\":true},{\"url\":\"https://www.kongzhongjr.com/wap/activity/integral-draw2-new.html\",\"title\":\"积分翻翻乐\",\"img\":\"//jrpubimg.kongzhongjr.com/banner_img/20180403061706_积分翻翻乐.jpg\",\"loginEnterType\":false},{\"url\":\"https://www.kongzhongjr.com/wap/activity/integral-draw-new.html\",\"title\":\"小积分抽大奖\",\"img\":\"//jrpubimg.kongzhongjr.com/banner_img/20180403061639_积分抽大奖.jpg\",\"loginEnterType\":false}],\"pointsMallProductList\":[{\"id\":423,\"name\":\"Apple iPhoneX 256G\",\"points\":160000.0,\"prePoints\":200000.0,\"picUrl\":\"https://jrpubimg.kongzhongjr.com/pointsmall/20171023031150_8X-256G.jpg\",\"url\":\"/wap/pointshop/detail.html?id=423\"},{\"id\":566,\"name\":\"戴尔P2717H 27英显示器\",\"points\":22500.0,\"prePoints\":30000.0,\"picUrl\":\"https://jrpubimg.kongzhongjr.com/pointsmall/20180402062453_20180309121713_1520569010(1).jpg\",\"url\":\"/wap/pointshop/detail.html?id=566\"},{\"id\":480,\"name\":\"Bose?QC25\",\"points\":35200.0,\"prePoints\":50000.0,\"picUrl\":\"https://jrpubimg.kongzhongjr.com/pointsmall/20180402062751_1.jpg\",\"url\":\"/wap/pointshop/detail.html?id=480\"},{\"id\":376,\"name\":\"Kindle Paperwhite\",\"points\":11600.0,\"prePoints\":-1.0,\"picUrl\":\"https://jrpubimg.kongzhongjr.com/pointsmall/20170821094602_1503279280(1).jpg\",\"url\":\"/wap/pointshop/detail.html?id=376\"},{\"id\":336,\"name\":\"索尼ILCE-6000L微单\",\"points\":48000.0,\"prePoints\":-1.0,\"picUrl\":\"https://jrpubimg.kongzhongjr.com/pointsmall/20170810065707_1502161320(1).jpg\",\"url\":\"/wap/pointshop/detail.html?id=336\"},{\"id\":337,\"name\":\"2018Apple iPad平板\",\"points\":42000.0,\"prePoints\":50000.0,\"picUrl\":\"https://jrpubimg.kongzhongjr.com/pointsmall/20180523030619_ipad2018.png\",\"url\":\"/wap/pointshop/detail.html?id=337\"}],\"discoverNewsList\":[{\"newsId\":546,\"title\":\"紧贴普惠金融践行合规 空中金融荣膺“新金融合规发展”奖\",\"subTitle\":\"本次峰旨在共同探讨金融科技发展与监管的前沿话题。\",\"outLink\":\"/wap/activity/notic.html?id=546\",\"imgUrl\":\"//jrpubimg.kongzhongjr.com/banner_img/20180702022808_IMG117_副本.jpg\",\"typeName\":\"媒体报道\"},{\"newsId\":538,\"title\":\"空中金融谈热点：提振股市是解药，缓解流动性压力已成当务之急！\",\"subTitle\":\"无论是金融还是实体，都在面临着资金断流的威胁\",\"outLink\":\"/wap/activity/notic.html?id=538\",\"imgUrl\":\"//jrpubimg.kongzhongjr.com/banner_img/20180615044334_QQ截图20180615164221_副本.jpg\",\"typeName\":\"理财资讯\"},{\"newsId\":536,\"title\":\"空中金融讲段子：明明可以靠爹的非要拼实力——盘点那些圈子里的富二代\",\"subTitle\":\"变形金刚这部电影相信很多人都非常熟悉了！\",\"outLink\":\"/wap/activity/notic.html?id=536\",\"imgUrl\":\"//jrpubimg.kongzhongjr.com/banner_img/20180614114301_QQ截图20180614114131_副本.jpg\",\"typeName\":\"理财资讯\"},{\"newsId\":535,\"title\":\"空中金融谈热点：揭露血汗工厂——为什么美国人要帮中国劳工说话\",\"subTitle\":\"不论是出于何种目的，对于企业压榨劳工的行为都应当被声讨和谴责\",\"outLink\":\"/wap/activity/notic.html?id=535\",\"imgUrl\":\"//jrpubimg.kongzhongjr.com/banner_img/20180612065430_timg (10)_副本.jpg\",\"typeName\":\"理财资讯\"}],\"customerServiceNumber\":\"400-920-7872\",\"customerServiceTime\":\"工作日9:30-18:30\",\"needLoginForShop\":true},\"isLogin\":false}\n";
    class IntegerComparable  extends Point<Integer> implements Comparable<IntegerComparable>,Serializable{

        IntegerComparable(Integer x,Integer y){
            super(x,y);
        }
        @Override
        public boolean comparable(IntegerComparable integerComparable) {
            return integerComparable.getX()>getX();
        }
    }

    /**
     * https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。

     给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     * @param digits
     * @return
     */
    public List<String> letterCombinations(String digits) {
        List<String> result=new ArrayList<>();
        HashMap<String, List<String>> data=new HashMap<>();
        data.put("2", Arrays.asList("a","b","c"));
        data.put("3",Arrays.asList("d","e","f"));
        data.put("4",Arrays.asList("g","h","i"));
        data.put("5",Arrays.asList("j","k","l"));
        data.put("6",Arrays.asList("m","n","o"));
        data.put("7",Arrays.asList("p","q","r","s"));
        data.put("8",Arrays.asList("t","u","v"));
        data.put("9",Arrays.asList("w","x","y","z"));


        for(int i=0;i<digits.length();i++){
            StringBuilder sb=new StringBuilder();
            List<String> item=data.get(String.valueOf(digits.charAt(i)));

            for(int j=0;j<item.size();j++){
                String randomStr=item.get(j);
                sb.append(randomStr);
            }
            result.add(sb.toString());
        }
        return result;
    }

}
