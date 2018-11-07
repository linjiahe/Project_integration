package com.blockchain.commune.mapper;

import com.blockchain.commune.model.PurseTranslog;
import com.blockchain.commune.model.PurseTranslogCriteria.Criteria;
import com.blockchain.commune.model.PurseTranslogCriteria.Criterion;
import com.blockchain.commune.model.PurseTranslogCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class PurseTranslogSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_translog
     *
     * @mbg.generated
     */
    public String countByExample(PurseTranslogCriteria example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("purse_translog");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_translog
     *
     * @mbg.generated
     */
    public String deleteByExample(PurseTranslogCriteria example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("purse_translog");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_translog
     *
     * @mbg.generated
     */
    public String insertSelective(PurseTranslog record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("purse_translog");
        
        if (record.getTransId() != null) {
            sql.VALUES("trans_id", "#{transId,jdbcType=VARCHAR}");
        }
        
        if (record.getRelaTransId() != null) {
            sql.VALUES("rela_trans_id", "#{relaTransId,jdbcType=VARCHAR}");
        }
        
        if (record.getTransType() != null) {
            sql.VALUES("trans_type", "#{transType,jdbcType=VARCHAR}");
        }
        
        if (record.getAccountId() != null) {
            sql.VALUES("account_id", "#{accountId,jdbcType=VARCHAR}");
        }
        
        if (record.getSubAccountId() != null) {
            sql.VALUES("sub_account_id", "#{subAccountId,jdbcType=VARCHAR}");
        }
        
        if (record.getSubAccountType() != null) {
            sql.VALUES("sub_account_type", "#{subAccountType,jdbcType=VARCHAR}");
        }
        
        if (record.getTransTitle() != null) {
            sql.VALUES("trans_title", "#{transTitle,jdbcType=VARCHAR}");
        }
        
        if (record.getTransAmount() != null) {
            sql.VALUES("trans_amount", "#{transAmount,jdbcType=DECIMAL}");
        }
        
        if (record.getTransDirector() != null) {
            sql.VALUES("trans_director", "#{transDirector,jdbcType=VARCHAR}");
        }
        
        if (record.getPartyUserid() != null) {
            sql.VALUES("party_userId", "#{partyUserid,jdbcType=VARCHAR}");
        }
        
        if (record.getTransParty() != null) {
            sql.VALUES("trans_party", "#{transParty,jdbcType=VARCHAR}");
        }
        
        if (record.getUpanddown() != null) {
            sql.VALUES("upAndDown", "#{upanddown,jdbcType=TINYINT}");
        }
        
        if (record.getSourceBalance() != null) {
            sql.VALUES("source_balance", "#{sourceBalance,jdbcType=DECIMAL}");
        }
        
        if (record.getTageetBalance() != null) {
            sql.VALUES("tageet_balance", "#{tageetBalance,jdbcType=DECIMAL}");
        }
        
        if (record.getReverseStatus() != null) {
            sql.VALUES("reverse_status", "#{reverseStatus,jdbcType=TINYINT}");
        }
        
        if (record.getDisplayStatus() != null) {
            sql.VALUES("display_status", "#{displayStatus,jdbcType=TINYINT}");
        }
        
        if (record.getIsBlockTrans() != null) {
            sql.VALUES("is_block_trans", "#{isBlockTrans,jdbcType=TINYINT}");
        }
        
        if (record.getTransDetailId() != null) {
            sql.VALUES("trans_detail_id", "#{transDetailId,jdbcType=VARCHAR}");
        }
        
        if (record.getBlockEndTime() != null) {
            sql.VALUES("block_end_time", "#{blockEndTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getRemark() != null) {
            sql.VALUES("remark", "#{remark,jdbcType=VARCHAR}");
        }
        
        if (record.getOrderStatus() != null) {
            sql.VALUES("order_status", "#{orderStatus,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_translog
     *
     * @mbg.generated
     */
    public String selectByExample(PurseTranslogCriteria example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("trans_id");
        } else {
            sql.SELECT("trans_id");
        }
        sql.SELECT("rela_trans_id");
        sql.SELECT("trans_type");
        sql.SELECT("account_id");
        sql.SELECT("sub_account_id");
        sql.SELECT("sub_account_type");
        sql.SELECT("trans_title");
        sql.SELECT("trans_amount");
        sql.SELECT("trans_director");
        sql.SELECT("party_userId");
        sql.SELECT("trans_party");
        sql.SELECT("upAndDown");
        sql.SELECT("source_balance");
        sql.SELECT("tageet_balance");
        sql.SELECT("reverse_status");
        sql.SELECT("display_status");
        sql.SELECT("is_block_trans");
        sql.SELECT("trans_detail_id");
        sql.SELECT("block_end_time");
        sql.SELECT("remark");
        sql.SELECT("order_status");
        sql.SELECT("create_time");
        sql.SELECT("update_time");
        sql.FROM("purse_translog");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_translog
     *
     * @mbg.generated
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        PurseTranslog record = (PurseTranslog) parameter.get("record");
        PurseTranslogCriteria example = (PurseTranslogCriteria) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("purse_translog");
        
        if (record.getTransId() != null) {
            sql.SET("trans_id = #{record.transId,jdbcType=VARCHAR}");
        }
        
        if (record.getRelaTransId() != null) {
            sql.SET("rela_trans_id = #{record.relaTransId,jdbcType=VARCHAR}");
        }
        
        if (record.getTransType() != null) {
            sql.SET("trans_type = #{record.transType,jdbcType=VARCHAR}");
        }
        
        if (record.getAccountId() != null) {
            sql.SET("account_id = #{record.accountId,jdbcType=VARCHAR}");
        }
        
        if (record.getSubAccountId() != null) {
            sql.SET("sub_account_id = #{record.subAccountId,jdbcType=VARCHAR}");
        }
        
        if (record.getSubAccountType() != null) {
            sql.SET("sub_account_type = #{record.subAccountType,jdbcType=VARCHAR}");
        }
        
        if (record.getTransTitle() != null) {
            sql.SET("trans_title = #{record.transTitle,jdbcType=VARCHAR}");
        }
        
        if (record.getTransAmount() != null) {
            sql.SET("trans_amount = #{record.transAmount,jdbcType=DECIMAL}");
        }
        
        if (record.getTransDirector() != null) {
            sql.SET("trans_director = #{record.transDirector,jdbcType=VARCHAR}");
        }
        
        if (record.getPartyUserid() != null) {
            sql.SET("party_userId = #{record.partyUserid,jdbcType=VARCHAR}");
        }
        
        if (record.getTransParty() != null) {
            sql.SET("trans_party = #{record.transParty,jdbcType=VARCHAR}");
        }
        
        if (record.getUpanddown() != null) {
            sql.SET("upAndDown = #{record.upanddown,jdbcType=TINYINT}");
        }
        
        if (record.getSourceBalance() != null) {
            sql.SET("source_balance = #{record.sourceBalance,jdbcType=DECIMAL}");
        }
        
        if (record.getTageetBalance() != null) {
            sql.SET("tageet_balance = #{record.tageetBalance,jdbcType=DECIMAL}");
        }
        
        if (record.getReverseStatus() != null) {
            sql.SET("reverse_status = #{record.reverseStatus,jdbcType=TINYINT}");
        }
        
        if (record.getDisplayStatus() != null) {
            sql.SET("display_status = #{record.displayStatus,jdbcType=TINYINT}");
        }
        
        if (record.getIsBlockTrans() != null) {
            sql.SET("is_block_trans = #{record.isBlockTrans,jdbcType=TINYINT}");
        }
        
        if (record.getTransDetailId() != null) {
            sql.SET("trans_detail_id = #{record.transDetailId,jdbcType=VARCHAR}");
        }
        
        if (record.getBlockEndTime() != null) {
            sql.SET("block_end_time = #{record.blockEndTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getRemark() != null) {
            sql.SET("remark = #{record.remark,jdbcType=VARCHAR}");
        }
        
        if (record.getOrderStatus() != null) {
            sql.SET("order_status = #{record.orderStatus,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_translog
     *
     * @mbg.generated
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("purse_translog");
        
        sql.SET("trans_id = #{record.transId,jdbcType=VARCHAR}");
        sql.SET("rela_trans_id = #{record.relaTransId,jdbcType=VARCHAR}");
        sql.SET("trans_type = #{record.transType,jdbcType=VARCHAR}");
        sql.SET("account_id = #{record.accountId,jdbcType=VARCHAR}");
        sql.SET("sub_account_id = #{record.subAccountId,jdbcType=VARCHAR}");
        sql.SET("sub_account_type = #{record.subAccountType,jdbcType=VARCHAR}");
        sql.SET("trans_title = #{record.transTitle,jdbcType=VARCHAR}");
        sql.SET("trans_amount = #{record.transAmount,jdbcType=DECIMAL}");
        sql.SET("trans_director = #{record.transDirector,jdbcType=VARCHAR}");
        sql.SET("party_userId = #{record.partyUserid,jdbcType=VARCHAR}");
        sql.SET("trans_party = #{record.transParty,jdbcType=VARCHAR}");
        sql.SET("upAndDown = #{record.upanddown,jdbcType=TINYINT}");
        sql.SET("source_balance = #{record.sourceBalance,jdbcType=DECIMAL}");
        sql.SET("tageet_balance = #{record.tageetBalance,jdbcType=DECIMAL}");
        sql.SET("reverse_status = #{record.reverseStatus,jdbcType=TINYINT}");
        sql.SET("display_status = #{record.displayStatus,jdbcType=TINYINT}");
        sql.SET("is_block_trans = #{record.isBlockTrans,jdbcType=TINYINT}");
        sql.SET("trans_detail_id = #{record.transDetailId,jdbcType=VARCHAR}");
        sql.SET("block_end_time = #{record.blockEndTime,jdbcType=TIMESTAMP}");
        sql.SET("remark = #{record.remark,jdbcType=VARCHAR}");
        sql.SET("order_status = #{record.orderStatus,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        
        PurseTranslogCriteria example = (PurseTranslogCriteria) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_translog
     *
     * @mbg.generated
     */
    public String updateByPrimaryKeySelective(PurseTranslog record) {
        SQL sql = new SQL();
        sql.UPDATE("purse_translog");
        
        if (record.getRelaTransId() != null) {
            sql.SET("rela_trans_id = #{relaTransId,jdbcType=VARCHAR}");
        }
        
        if (record.getTransType() != null) {
            sql.SET("trans_type = #{transType,jdbcType=VARCHAR}");
        }
        
        if (record.getAccountId() != null) {
            sql.SET("account_id = #{accountId,jdbcType=VARCHAR}");
        }
        
        if (record.getSubAccountId() != null) {
            sql.SET("sub_account_id = #{subAccountId,jdbcType=VARCHAR}");
        }
        
        if (record.getSubAccountType() != null) {
            sql.SET("sub_account_type = #{subAccountType,jdbcType=VARCHAR}");
        }
        
        if (record.getTransTitle() != null) {
            sql.SET("trans_title = #{transTitle,jdbcType=VARCHAR}");
        }
        
        if (record.getTransAmount() != null) {
            sql.SET("trans_amount = #{transAmount,jdbcType=DECIMAL}");
        }
        
        if (record.getTransDirector() != null) {
            sql.SET("trans_director = #{transDirector,jdbcType=VARCHAR}");
        }
        
        if (record.getPartyUserid() != null) {
            sql.SET("party_userId = #{partyUserid,jdbcType=VARCHAR}");
        }
        
        if (record.getTransParty() != null) {
            sql.SET("trans_party = #{transParty,jdbcType=VARCHAR}");
        }
        
        if (record.getUpanddown() != null) {
            sql.SET("upAndDown = #{upanddown,jdbcType=TINYINT}");
        }
        
        if (record.getSourceBalance() != null) {
            sql.SET("source_balance = #{sourceBalance,jdbcType=DECIMAL}");
        }
        
        if (record.getTageetBalance() != null) {
            sql.SET("tageet_balance = #{tageetBalance,jdbcType=DECIMAL}");
        }
        
        if (record.getReverseStatus() != null) {
            sql.SET("reverse_status = #{reverseStatus,jdbcType=TINYINT}");
        }
        
        if (record.getDisplayStatus() != null) {
            sql.SET("display_status = #{displayStatus,jdbcType=TINYINT}");
        }
        
        if (record.getIsBlockTrans() != null) {
            sql.SET("is_block_trans = #{isBlockTrans,jdbcType=TINYINT}");
        }
        
        if (record.getTransDetailId() != null) {
            sql.SET("trans_detail_id = #{transDetailId,jdbcType=VARCHAR}");
        }
        
        if (record.getBlockEndTime() != null) {
            sql.SET("block_end_time = #{blockEndTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getRemark() != null) {
            sql.SET("remark = #{remark,jdbcType=VARCHAR}");
        }
        
        if (record.getOrderStatus() != null) {
            sql.SET("order_status = #{orderStatus,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("trans_id = #{transId,jdbcType=VARCHAR}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table purse_translog
     *
     * @mbg.generated
     */
    protected void applyWhere(SQL sql, PurseTranslogCriteria example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}