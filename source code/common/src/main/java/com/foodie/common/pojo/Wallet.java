package com.foodie.common.pojo;

public class Wallet {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wallet.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wallet.balance
     *
     * @mbg.generated
     */
    private Long balance;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wallet.state
     *
     * @mbg.generated
     */
    private Byte state;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wallet.userid
     *
     * @mbg.generated
     */
    private Integer userid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wallet.id
     *
     * @return the value of wallet.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wallet.id
     *
     * @param id the value for wallet.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wallet.balance
     *
     * @return the value of wallet.balance
     *
     * @mbg.generated
     */
    public Long getBalance() {
        return balance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wallet.balance
     *
     * @param balance the value for wallet.balance
     *
     * @mbg.generated
     */
    public void setBalance(Long balance) {
        this.balance = balance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wallet.state
     *
     * @return the value of wallet.state
     *
     * @mbg.generated
     */
    public Byte getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wallet.state
     *
     * @param state the value for wallet.state
     *
     * @mbg.generated
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wallet.userid
     *
     * @return the value of wallet.userid
     *
     * @mbg.generated
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wallet.userid
     *
     * @param userid the value for wallet.userid
     *
     * @mbg.generated
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}