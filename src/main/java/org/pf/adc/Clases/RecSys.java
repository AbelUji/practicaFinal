package org.pf.adc.Clases;

import org.pf.adc.Constructores.Row;
import org.pf.adc.Constructores.Table;
import org.pf.adc.Excepciones.ClusterException;
import org.pf.adc.Interfaces.Algorithm;

import java.util.*;

public class RecSys {
    private Algorithm algorithm;
    private Map<Integer,List<String>> grupos;
    public RecSys(Algorithm algorithm){
        super();
        this.algorithm=algorithm;
        this.grupos=new HashMap<>();
    }

    public void train(Table trainData) throws ClusterException {
        algorithm.train(trainData);
    }

    public void run(Table testData, List<String> testItemNames){
        for(int i=0;i<testData.getRows().size();i++){
            int numClase=algorithm.estimate(testData.getRows().get(i).getData());
            if(!grupos.containsKey(numClase)){
                grupos.put(numClase,new ArrayList<>());
            }
            grupos.get(numClase).add(testItemNames.get(i));
        }
    }
    public List<String> recommend(String nameLikedItem, int numRecommendations){
        int numClase=findName(nameLikedItem);
        if(numClase!=-1){
            List<String> devolver=new ArrayList<>();
            for(int i=0;i<grupos.get(numClase).size() && devolver.size()<numRecommendations;i++){
                String name=grupos.get(numClase).get(i);
                if(name.compareTo(nameLikedItem)!=0 && !devolver.contains(name)){
                    devolver.add(name);
                }
            }
            return devolver;
        }else
            return null;
    }
    public Integer findName(String nameLikedItem){
        for (Integer key:grupos.keySet()){
            if(grupos.get(key).contains(nameLikedItem)){
                return key;
            }
        }
        return -1;
    }

    public int getGrupo(String cancion){
        int grupo=findName(cancion);
        return this.grupos.get(grupo).size();
    }
}