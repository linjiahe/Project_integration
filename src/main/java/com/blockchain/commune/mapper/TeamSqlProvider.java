package com.blockchain.commune.mapper;

import com.blockchain.commune.model.Team;
import com.blockchain.commune.model.TeamCriteria.Criteria;
import com.blockchain.commune.model.TeamCriteria.Criterion;
import com.blockchain.commune.model.TeamCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class TeamSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team
     *
     * @mbg.generated
     */
    public String countByExample(TeamCriteria example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("team");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team
     *
     * @mbg.generated
     */
    public String deleteByExample(TeamCriteria example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("team");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team
     *
     * @mbg.generated
     */
    public String insertSelective(Team record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("team");
        
        if (record.getUserId() != null) {
            sql.VALUES("user_id", "#{userId,jdbcType=VARCHAR}");
        }
        
        if (record.getMemberAmount() != null) {
            sql.VALUES("member_amount", "#{memberAmount,jdbcType=INTEGER}");
        }
        
        if (record.getDirectMemberAmount() != null) {
            sql.VALUES("direct_member_amount", "#{directMemberAmount,jdbcType=INTEGER}");
        }
        
        if (record.getMemberLevel() != null) {
            sql.VALUES("member_level", "#{memberLevel,jdbcType=INTEGER}");
        }
        
        if (record.getPresidentAmount() != null) {
            sql.VALUES("president_amount", "#{presidentAmount,jdbcType=INTEGER}");
        }
        
        if (record.getDirectPresidentAmount() != null) {
            sql.VALUES("direct_president_amount", "#{directPresidentAmount,jdbcType=INTEGER}");
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
     * This method corresponds to the database table team
     *
     * @mbg.generated
     */
    public String selectByExample(TeamCriteria example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("user_id");
        } else {
            sql.SELECT("user_id");
        }
        sql.SELECT("member_amount");
        sql.SELECT("direct_member_amount");
        sql.SELECT("member_level");
        sql.SELECT("president_amount");
        sql.SELECT("direct_president_amount");
        sql.SELECT("create_time");
        sql.SELECT("update_time");
        sql.FROM("team");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team
     *
     * @mbg.generated
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        Team record = (Team) parameter.get("record");
        TeamCriteria example = (TeamCriteria) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("team");
        
        if (record.getUserId() != null) {
            sql.SET("user_id = #{record.userId,jdbcType=VARCHAR}");
        }
        
        if (record.getMemberAmount() != null) {
            sql.SET("member_amount = #{record.memberAmount,jdbcType=INTEGER}");
        }
        
        if (record.getDirectMemberAmount() != null) {
            sql.SET("direct_member_amount = #{record.directMemberAmount,jdbcType=INTEGER}");
        }
        
        if (record.getMemberLevel() != null) {
            sql.SET("member_level = #{record.memberLevel,jdbcType=INTEGER}");
        }
        
        if (record.getPresidentAmount() != null) {
            sql.SET("president_amount = #{record.presidentAmount,jdbcType=INTEGER}");
        }
        
        if (record.getDirectPresidentAmount() != null) {
            sql.SET("direct_president_amount = #{record.directPresidentAmount,jdbcType=INTEGER}");
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
     * This method corresponds to the database table team
     *
     * @mbg.generated
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("team");
        
        sql.SET("user_id = #{record.userId,jdbcType=VARCHAR}");
        sql.SET("member_amount = #{record.memberAmount,jdbcType=INTEGER}");
        sql.SET("direct_member_amount = #{record.directMemberAmount,jdbcType=INTEGER}");
        sql.SET("member_level = #{record.memberLevel,jdbcType=INTEGER}");
        sql.SET("president_amount = #{record.presidentAmount,jdbcType=INTEGER}");
        sql.SET("direct_president_amount = #{record.directPresidentAmount,jdbcType=INTEGER}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        
        TeamCriteria example = (TeamCriteria) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team
     *
     * @mbg.generated
     */
    public String updateByPrimaryKeySelective(Team record) {
        SQL sql = new SQL();
        sql.UPDATE("team");
        
        if (record.getMemberAmount() != null) {
            sql.SET("member_amount = #{memberAmount,jdbcType=INTEGER}");
        }
        
        if (record.getDirectMemberAmount() != null) {
            sql.SET("direct_member_amount = #{directMemberAmount,jdbcType=INTEGER}");
        }
        
        if (record.getMemberLevel() != null) {
            sql.SET("member_level = #{memberLevel,jdbcType=INTEGER}");
        }
        
        if (record.getPresidentAmount() != null) {
            sql.SET("president_amount = #{presidentAmount,jdbcType=INTEGER}");
        }
        
        if (record.getDirectPresidentAmount() != null) {
            sql.SET("direct_president_amount = #{directPresidentAmount,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("user_id = #{userId,jdbcType=VARCHAR}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team
     *
     * @mbg.generated
     */
    protected void applyWhere(SQL sql, TeamCriteria example, boolean includeExamplePhrase) {
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