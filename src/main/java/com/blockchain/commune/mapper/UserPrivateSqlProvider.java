package com.blockchain.commune.mapper;

import com.blockchain.commune.model.UserPrivate;
import com.blockchain.commune.model.UserPrivateCriteria.Criteria;
import com.blockchain.commune.model.UserPrivateCriteria.Criterion;
import com.blockchain.commune.model.UserPrivateCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class UserPrivateSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_private
     *
     * @mbg.generated
     */
    public String countByExample(UserPrivateCriteria example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("user_private");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_private
     *
     * @mbg.generated
     */
    public String deleteByExample(UserPrivateCriteria example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("user_private");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_private
     *
     * @mbg.generated
     */
    public String insertSelective(UserPrivate record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("user_private");
        
        if (record.getUserId() != null) {
            sql.VALUES("user_id", "#{userId,jdbcType=VARCHAR}");
        }
        
        if (record.getPaymentPassword() != null) {
            sql.VALUES("payment_password", "#{paymentPassword,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_private
     *
     * @mbg.generated
     */
    public String selectByExample(UserPrivateCriteria example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("user_id");
        } else {
            sql.SELECT("user_id");
        }
        sql.SELECT("payment_password");
        sql.SELECT("update_time");
        sql.SELECT("create_time");
        sql.FROM("user_private");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_private
     *
     * @mbg.generated
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        UserPrivate record = (UserPrivate) parameter.get("record");
        UserPrivateCriteria example = (UserPrivateCriteria) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("user_private");
        
        if (record.getUserId() != null) {
            sql.SET("user_id = #{record.userId,jdbcType=VARCHAR}");
        }
        
        if (record.getPaymentPassword() != null) {
            sql.SET("payment_password = #{record.paymentPassword,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_private
     *
     * @mbg.generated
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("user_private");
        
        sql.SET("user_id = #{record.userId,jdbcType=VARCHAR}");
        sql.SET("payment_password = #{record.paymentPassword,jdbcType=VARCHAR}");
        sql.SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        
        UserPrivateCriteria example = (UserPrivateCriteria) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_private
     *
     * @mbg.generated
     */
    public String updateByPrimaryKeySelective(UserPrivate record) {
        SQL sql = new SQL();
        sql.UPDATE("user_private");
        
        if (record.getPaymentPassword() != null) {
            sql.SET("payment_password = #{paymentPassword,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("user_id = #{userId,jdbcType=VARCHAR}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_private
     *
     * @mbg.generated
     */
    protected void applyWhere(SQL sql, UserPrivateCriteria example, boolean includeExamplePhrase) {
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