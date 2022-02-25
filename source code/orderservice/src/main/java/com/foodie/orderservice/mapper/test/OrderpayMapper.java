package com.foodie.orderservice.mapper.test;

import java.util.List;

import com.foodie.common.pojo.Orderpay;
import org.apache.ibatis.annotations.Param;

public interface OrderpayMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderpay
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderpay
     *
     * @mbg.generated
     */
    int insert(Orderpay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderpay
     *
     * @mbg.generated
     */
    Orderpay selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderpay
     *
     * @mbg.generated
     */
    List<Orderpay> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderpay
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Orderpay record);
}