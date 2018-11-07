package com.blockchain.commune.custommodel;

import java.math.BigDecimal;
import java.util.Date;

public class TransLogAdmin {

    private String trans_id;

    private String login_name;

    private String rela_Trans_Id;

    private String trans_type;

    private String account_id;

    private String sub_account_id;

    private String sub_account_type;

    private String trans_title;

    private BigDecimal trans_amount;

    private String trans_director;

    private BigDecimal source_balance;

    private BigDecimal tageet_balance;

    private Byte reverse_status;

    private Byte display_status;

    private Byte is_block_trans;

    private Date block_end_time;

    private String remark;

    private Date create_time;

    private Date update_time;

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getRela_Trans_Id() {
        return rela_Trans_Id;
    }

    public void setRela_Trans_Id(String rela_Trans_Id) {
        this.rela_Trans_Id = rela_Trans_Id;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getSub_account_id() {
        return sub_account_id;
    }

    public void setSub_account_id(String sub_account_id) {
        this.sub_account_id = sub_account_id;
    }

    public String getSub_account_type() {
        return sub_account_type;
    }

    public void setSub_account_type(String sub_account_type) {
        this.sub_account_type = sub_account_type;
    }

    public String getTrans_title() {
        return trans_title;
    }

    public void setTrans_title(String trans_title) {
        this.trans_title = trans_title;
    }

    public BigDecimal getTrans_amount() {
        return trans_amount;
    }

    public void setTrans_amount(BigDecimal trans_amount) {
        this.trans_amount = trans_amount;
    }

    public String getTrans_director() {
        return trans_director;
    }

    public void setTrans_director(String trans_director) {
        this.trans_director = trans_director;
    }

    public BigDecimal getSource_balance() {
        return source_balance;
    }

    public void setSource_balance(BigDecimal source_balance) {
        this.source_balance = source_balance;
    }

    public BigDecimal getTageet_balance() {
        return tageet_balance;
    }

    public void setTageet_balance(BigDecimal tageet_balance) {
        this.tageet_balance = tageet_balance;
    }

    public Byte getReverse_status() {
        return reverse_status;
    }

    public void setReverse_status(Byte reverse_status) {
        this.reverse_status = reverse_status;
    }

    public Byte getDisplay_status() {
        return display_status;
    }

    public void setDisplay_status(Byte display_status) {
        this.display_status = display_status;
    }

    public Byte getIs_block_trans() {
        return is_block_trans;
    }

    public void setIs_block_trans(Byte is_block_trans) {
        this.is_block_trans = is_block_trans;
    }

    public Date getBlock_end_time() {
        return block_end_time;
    }

    public void setBlock_end_time(Date block_end_time) {
        this.block_end_time = block_end_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}