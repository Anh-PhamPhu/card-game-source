package org.jeecg.modules.games.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.games.entity.CgResultAnalysis;
import org.jeecg.modules.games.mapper.CgResultAnalysisMapper;
import org.jeecg.modules.games.service.CgResultAnalysisService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author 神州
 * @date 2022年05月19日 14:43
 */
@Service
public class CgResultAnalysisServiceImpl extends ServiceImpl<CgResultAnalysisMapper, CgResultAnalysis> implements CgResultAnalysisService {


    @Override
    public JSONObject getNumber(List<Integer> param){
        List<CgResultAnalysis> list  = baseMapper.selectBatchIds(param);

        JSONObject jsonObject = new JSONObject();
        List<Integer> list1 = new ArrayList<>();
        list1.add(list.stream().mapToInt(CgResultAnalysis::getA).sum());
        list1.add(list.stream().mapToInt(CgResultAnalysis::getB).sum());
        list1.add(list.stream().mapToInt(CgResultAnalysis::getC).sum());
        list1.add(list.stream().mapToInt(CgResultAnalysis::getD).sum());
        list1.add(list.stream().mapToInt(CgResultAnalysis::getE).sum());
        list1.add(list.stream().mapToInt(CgResultAnalysis::getF).sum());
        list1.add(list.stream().mapToInt(CgResultAnalysis::getG).sum());
        list1.add(list.stream().mapToInt(CgResultAnalysis::getH).sum());
        list1.add(list.stream().mapToInt(CgResultAnalysis::getI).sum());
        jsonObject.put("resultList",list1);
        Integer max = Collections.max(list1);
        Integer index = list1.indexOf(max);
        String resultContent = "";
        if(index == 0){
            //A
            resultContent = "自身の専門分野において、知識・技能を高めることが好き。その力を発揮することで人の役に立ち、頼ってもらえる存在になることに価値を感じます。モチベーションが上がる言葉「頼りにしてます！」";
        }else if(index == 1){
            resultContent = "仲間とともに勝利や達成目標を目指すことが好き。組織において、周囲を鼓舞し統率することで共通の目的を成しえることに価値を感じます。モチベーションが上がる言葉「ついていきます！」";
        }else if(index == 2){
            resultContent = "指示や束縛を受けず、自身の考えで筋道や方法を考えるのが好き。自分の信念をもって仕事を完遂させ、周囲を納得させることに価値を感じます。モチベーションが上がる言葉「あなたに任せます！」";
        }
        else if(index == 3){
            resultContent = "将来に不安のない安心できる状態が好き。目的が明確で、繰り返すこと、続けることで確実に成果が得られる心に余裕をもって就ける仕事に価値を感じます。モチベーションが上がる言葉「大丈夫です！」";
        }
        else if(index == 4){
            resultContent = "自らの着眼や発想で新しいものを生み出すことが好き。これまでにないユニークな商品やサービスの開発などで、世の中に画期的な変化をもたらす活動に価値を感じます。モチベーションが上がる言葉「こんなの初めて！」";
        }
        else if(index == 5){
            resultContent = "困っている人への手助けを行い、喜んでもらえることが好き。世の中をよりよくするための活動に尽力・協力し、他者を救う社会活動に携わることに価値を感じます。モチベーションが上がる言葉「感謝しています！」";
        }
        else if(index == 6){
            resultContent = "目の前にある困難な状況に挑み、解決することが好き。これまで解決に至っていない社会や事業上の問題を克服し、新たな道筋を切り拓くことに価値を感じます。モチベーションが上がる言葉「チャレンジですね！」";
        }
        else if(index == 7){
            resultContent = "仕事も日々の暮らしも自然体で柔軟にこなすことが好き。合理性も踏まえながら、暮らしと仕事をうまく融合し、相乗効果でよりよい結果を出すことに価値を感じます。モチベーションが上がる言葉「いい感じですね！」";
        }
        else if(index == 8){
            resultContent = "自身のパフォーマンスで、人を喜ばせることが好き。物事の事象やメッセージを自身の好みの表現方法で、人に伝え、心を揺り動かすことに価値を感じます。モチベーションが上がる言葉「感動しました！」";
        }
        jsonObject.put("detailContent",resultContent);
        return jsonObject;

    }
}
