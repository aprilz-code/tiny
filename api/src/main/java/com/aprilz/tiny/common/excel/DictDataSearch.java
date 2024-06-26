//package com.aprilz.tiny.common.excel;
//
//import com.aprilz.excel.core.dict.DictDataApi;
//import com.aprilz.excel.core.dto.DictDataDTO;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author Aprilz
// * @date 2023/2/22-19:56
// * @description excel字典实现类
// */
//@Service
//public class DictDataSearch extends DictDataApi {
//
//    private List<DictDataDTO> list = new ArrayList<>();
//
//    {
//        DictDataDTO dictDataDTO = new DictDataDTO();
//        dictDataDTO.setDictType("dic_sex");
//        dictDataDTO.setKey("1");
//        dictDataDTO.setValue("男");
//        list.add(dictDataDTO);
//        DictDataDTO dictDataDTO2 = new DictDataDTO();
//        dictDataDTO2.setDictType("dic_sex");
//        dictDataDTO2.setKey("0");
//        dictDataDTO2.setValue("女");
//        list.add(dictDataDTO2);
//        DictDataDTO dictDataDTO3 = new DictDataDTO();
//        dictDataDTO3.setDictType("dic_sex");
//        dictDataDTO3.setKey("2");
//        dictDataDTO3.setValue("不详");
//        list.add(dictDataDTO3);
//    }
//
//
//    public DictDataDTO getDictDataByKey(String dictType, String key) {
//        for (DictDataDTO dictDataDTO : list) {
//            if (dictDataDTO.getDictType().equals(dictType) && dictDataDTO.getKey().equals(key)) {
//                return dictDataDTO;
//            }
//        }
//        return new DictDataDTO();
//    }
//
//    public DictDataDTO getDictDataByValue(String dictType, String value) {
//        for (DictDataDTO dictDataDTO : list) {
//            if (dictDataDTO.getDictType().equals(dictType) && dictDataDTO.getValue().equals(value)) {
//                return dictDataDTO;
//            }
//        }
//        return new DictDataDTO();
//    }
//
//    @Override
//    public String[] getSource(String value, String params) {
//        return list.stream().map(DictDataDTO::getValue).collect(Collectors.toList()).toArray(new String[0]);
//    }
//}
