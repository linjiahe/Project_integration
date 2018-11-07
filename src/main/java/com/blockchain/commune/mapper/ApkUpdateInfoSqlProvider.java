package com.blockchain.commune.mapper;

import com.blockchain.commune.model.ApkUpdateInfo;
import com.blockchain.commune.model.ApkUpdateInfoCriteria.Criteria;
import com.blockchain.commune.model.ApkUpdateInfoCriteria.Criterion;
import com.blockchain.commune.model.ApkUpdateInfoCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class ApkUpdateInfoSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table apk_update_info
     *
     * @mbg.generated
     */
    public String countByExample(ApkUpdateInfoCriteria example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("apk_update_info");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table apk_update_info
     *
     * @mbg.generated
     */
    public String deleteByExample(ApkUpdateInfoCriteria example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("apk_update_info");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table apk_update_info
     *
     * @mbg.generated
     */
    public String insertSelective(ApkUpdateInfo record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("apk_update_info");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getNewVersion() != null) {
            sql.VALUES("new_version", "#{newVersion,jdbcType=VARCHAR}");
        }
        
        if (record.getMiniVersion() != null) {
            sql.VALUES("mini_version", "#{miniVersion,jdbcType=INTEGER}");
        }
        
        if (record.getApkQrcode() != null) {
            sql.VALUES("apk_qrcode", "#{apkQrcode,jdbcType=VARCHAR}");
        }
        
        if (record.getApkUrl() != null) {
            sql.VALUES("apk_url", "#{apkUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getApkDesc() != null) {
            sql.VALUES("apk_desc", "#{apkDesc,jdbcType=VARCHAR}");
        }
        
        if (record.getPlatform() != null) {
            sql.VALUES("platform", "#{platform,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getVersionCode() != null) {
            sql.VALUES("version_code", "#{versionCode,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table apk_update_info
     *
     * @mbg.generated
     */
    public String selectByExample(ApkUpdateInfoCriteria example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("new_version");
        sql.SELECT("mini_version");
        sql.SELECT("apk_qrcode");
        sql.SELECT("apk_url");
        sql.SELECT("apk_desc");
        sql.SELECT("platform");
        sql.SELECT("create_time");
        sql.SELECT("version_code");
        sql.FROM("apk_update_info");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table apk_update_info
     *
     * @mbg.generated
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        ApkUpdateInfo record = (ApkUpdateInfo) parameter.get("record");
        ApkUpdateInfoCriteria example = (ApkUpdateInfoCriteria) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("apk_update_info");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getNewVersion() != null) {
            sql.SET("new_version = #{record.newVersion,jdbcType=VARCHAR}");
        }
        
        if (record.getMiniVersion() != null) {
            sql.SET("mini_version = #{record.miniVersion,jdbcType=INTEGER}");
        }
        
        if (record.getApkQrcode() != null) {
            sql.SET("apk_qrcode = #{record.apkQrcode,jdbcType=VARCHAR}");
        }
        
        if (record.getApkUrl() != null) {
            sql.SET("apk_url = #{record.apkUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getApkDesc() != null) {
            sql.SET("apk_desc = #{record.apkDesc,jdbcType=VARCHAR}");
        }
        
        if (record.getPlatform() != null) {
            sql.SET("platform = #{record.platform,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getVersionCode() != null) {
            sql.SET("version_code = #{record.versionCode,jdbcType=INTEGER}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table apk_update_info
     *
     * @mbg.generated
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("apk_update_info");
        
        sql.SET("id = #{record.id,jdbcType=INTEGER}");
        sql.SET("new_version = #{record.newVersion,jdbcType=VARCHAR}");
        sql.SET("mini_version = #{record.miniVersion,jdbcType=INTEGER}");
        sql.SET("apk_qrcode = #{record.apkQrcode,jdbcType=VARCHAR}");
        sql.SET("apk_url = #{record.apkUrl,jdbcType=VARCHAR}");
        sql.SET("apk_desc = #{record.apkDesc,jdbcType=VARCHAR}");
        sql.SET("platform = #{record.platform,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("version_code = #{record.versionCode,jdbcType=INTEGER}");
        
        ApkUpdateInfoCriteria example = (ApkUpdateInfoCriteria) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table apk_update_info
     *
     * @mbg.generated
     */
    public String updateByPrimaryKeySelective(ApkUpdateInfo record) {
        SQL sql = new SQL();
        sql.UPDATE("apk_update_info");
        
        if (record.getNewVersion() != null) {
            sql.SET("new_version = #{newVersion,jdbcType=VARCHAR}");
        }
        
        if (record.getMiniVersion() != null) {
            sql.SET("mini_version = #{miniVersion,jdbcType=INTEGER}");
        }
        
        if (record.getApkQrcode() != null) {
            sql.SET("apk_qrcode = #{apkQrcode,jdbcType=VARCHAR}");
        }
        
        if (record.getApkUrl() != null) {
            sql.SET("apk_url = #{apkUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getApkDesc() != null) {
            sql.SET("apk_desc = #{apkDesc,jdbcType=VARCHAR}");
        }
        
        if (record.getPlatform() != null) {
            sql.SET("platform = #{platform,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getVersionCode() != null) {
            sql.SET("version_code = #{versionCode,jdbcType=INTEGER}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table apk_update_info
     *
     * @mbg.generated
     */
    protected void applyWhere(SQL sql, ApkUpdateInfoCriteria example, boolean includeExamplePhrase) {
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