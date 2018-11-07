package com.blockchain.commune.mapper;

import com.blockchain.commune.model.MarketTag;
import com.blockchain.commune.model.MarketTagCriteria.Criteria;
import com.blockchain.commune.model.MarketTagCriteria.Criterion;
import com.blockchain.commune.model.MarketTagCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class MarketTagSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_tag
     *
     * @mbg.generated
     */
    public String countByExample(MarketTagCriteria example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("market_tag");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_tag
     *
     * @mbg.generated
     */
    public String deleteByExample(MarketTagCriteria example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("market_tag");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_tag
     *
     * @mbg.generated
     */
    public String insertSelective(MarketTag record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("market_tag");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=VARCHAR}");
        }
        
        if (record.getTag() != null) {
            sql.VALUES("tag", "#{tag,jdbcType=VARCHAR}");
        }
        
        if (record.getUri() != null) {
            sql.VALUES("uri", "#{uri,jdbcType=VARCHAR}");
        }
        
        if (record.getTagType() != null) {
            sql.VALUES("tag_type", "#{tagType,jdbcType=VARCHAR}");
        }
        
        if (record.getDisable() != null) {
            sql.VALUES("disable", "#{disable,jdbcType=VARCHAR}");
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
     * This method corresponds to the database table market_tag
     *
     * @mbg.generated
     */
    public String selectByExample(MarketTagCriteria example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("tag");
        sql.SELECT("uri");
        sql.SELECT("tag_type");
        sql.SELECT("disable");
        sql.SELECT("create_time");
        sql.SELECT("update_time");
        sql.FROM("market_tag");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_tag
     *
     * @mbg.generated
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        MarketTag record = (MarketTag) parameter.get("record");
        MarketTagCriteria example = (MarketTagCriteria) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("market_tag");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=VARCHAR}");
        }
        
        if (record.getTag() != null) {
            sql.SET("tag = #{record.tag,jdbcType=VARCHAR}");
        }
        
        if (record.getUri() != null) {
            sql.SET("uri = #{record.uri,jdbcType=VARCHAR}");
        }
        
        if (record.getTagType() != null) {
            sql.SET("tag_type = #{record.tagType,jdbcType=VARCHAR}");
        }
        
        if (record.getDisable() != null) {
            sql.SET("disable = #{record.disable,jdbcType=VARCHAR}");
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
     * This method corresponds to the database table market_tag
     *
     * @mbg.generated
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("market_tag");
        
        sql.SET("id = #{record.id,jdbcType=VARCHAR}");
        sql.SET("tag = #{record.tag,jdbcType=VARCHAR}");
        sql.SET("uri = #{record.uri,jdbcType=VARCHAR}");
        sql.SET("tag_type = #{record.tagType,jdbcType=VARCHAR}");
        sql.SET("disable = #{record.disable,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        
        MarketTagCriteria example = (MarketTagCriteria) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_tag
     *
     * @mbg.generated
     */
    public String updateByPrimaryKeySelective(MarketTag record) {
        SQL sql = new SQL();
        sql.UPDATE("market_tag");
        
        if (record.getTag() != null) {
            sql.SET("tag = #{tag,jdbcType=VARCHAR}");
        }
        
        if (record.getUri() != null) {
            sql.SET("uri = #{uri,jdbcType=VARCHAR}");
        }
        
        if (record.getTagType() != null) {
            sql.SET("tag_type = #{tagType,jdbcType=VARCHAR}");
        }
        
        if (record.getDisable() != null) {
            sql.SET("disable = #{disable,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("id = #{id,jdbcType=VARCHAR}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_tag
     *
     * @mbg.generated
     */
    protected void applyWhere(SQL sql, MarketTagCriteria example, boolean includeExamplePhrase) {
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