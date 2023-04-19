package com.aprilz.tiny.common.api;


import cn.hutool.core.util.StrUtil;
import com.aprilz.tiny.common.utils.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期搜索参数
 *
 * @author aprilz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchVO implements Serializable {

    @ApiModelProperty(value = "起始日期")
    private String startDate;

    @ApiModelProperty(value = "结束日期")
    private String endDate;

    public Date getConvertStartDate() {
        if (StrUtil.isEmpty(startDate)) {
            return null;
        }
        return DateUtil.toDate(startDate, DateUtil.STANDARD_DATE_FORMAT);
    }

    public Date getConvertEndDate() {
        if (StrUtil.isEmpty(endDate)) {
            return null;
        }
        //结束时间等于结束日期+1天 -1秒，
        Date date = DateUtil.toDate(endDate, DateUtil.STANDARD_DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        calendar.set(Calendar.SECOND, -1);
        return calendar.getTime();
    }
}
