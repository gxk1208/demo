package com.auto.demo.service;

import com.auto.demo.common.PageParam;
import com.auto.demo.dto.AttachmentDto;
import com.auto.demo.dto.BackfillConfigDto;
import com.auto.demo.dto.LayoutConfigDto;
import com.auto.demo.entity.Attachment;
import com.auto.demo.entity.BackfillConfig;
import com.auto.demo.entity.FormLayout;
import com.auto.demo.entity.SelfField;
import com.auto.demo.param.BackfillParam;
import com.auto.demo.param.QueryAttachParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/5/26 13:41
 */
public interface DataService {

    /**
     * 添加数据
     * @param id  实体ID
     * @param map 添加的数据
     * @return
     */
    Integer addData(Integer id, Map<String, Object> map);

    /**
     * 获取数据
     * @param id 实体ID
     * @return
     */
    Map<String, Object> getAllData(PageParam pageParam, Integer id, Integer pname, String flag);

    /**
     *　修改数据
     * @param id    实体ID
     * @param list 修改的数据
     * @return
     */
    Integer updateData(Integer id, Map<String, Object> list);
    /**
     * 获取一条实体数据
     * @param id     实体id
     * @param dataId 数据id
     * @return
     */
    List<Map<String, Object>> getOneData(Integer id, Integer dataId);

    /**
     * 删除实体数据
     * @param id 实体ID
     * @param dataId 实体数据ID
     * @return
     */
    Integer deleteData(Integer id, Integer dataId);


    /**
     * 添加实体表单布局
     * @param layouts
     * @return
     */
     Integer  addLayout(List<FormLayout> layouts);

    /**
     * 添加实体表单布局,以及未布局的字段列表
     * @param entityId
     * @return
     */
    List<SelfField> getLayout(Integer entityId);

    /**
     * 获取实体名称字段的数据
     * @param id
     * @param query
     * @return
     */
    List<Map<String, Object>> getNameFieldValue(Integer id, String query);

    /**
     *获取用户新建编辑打开数据时的表单布局
     * @param entityId 实体id
     * @param dataId 数据id
     * @param flag 获取结果集的判断参数
     * @return
     */
    Map<String, Object> getForm(Integer entityId, Integer dataId, boolean flag);

    /**
     * 添加菜单布局
     * @param layoutConfigDto 菜单布局对象
     * @return
     */
    Integer addMenuLayout(LayoutConfigDto layoutConfigDto);

    /**
     * 获取所有菜单布局
     * @return
     */
    List<LayoutConfigDto> getMenuLayouts();

    /**
     * 获取指定的菜单布局
     * @param id
     * @return
     */
    LayoutConfigDto getMenuLayout(Integer id);
    /**
     * 获取默认的菜单布局
     * @return
     */
    LayoutConfigDto getDefaultMenuLayout();

    /**
     * 获取附件
     * @param param
     * @return
     */
    List<AttachmentDto> getAttach(QueryAttachParam param);

    /**
     * 分派数据
     * @param entityId 实体id
     * @param dataId 数据id
     * @param userId 用户id
     * @return
     */
    Integer dispatchData(Integer entityId,Integer dataId, Integer userId);

    /**
     * 导出数据
     * @param entityId 实体id
     * @param idList 数据集合
     * @param response
     * @throws IOException
     */
    void exportExcel(Integer entityId, String idList, HttpServletResponse response) throws IOException;

    /**
     * 添加表单回填配置
     * @param backfillConfig
     * @return
     */
    Integer saveBackfill(BackfillConfig backfillConfig);

    /**
     * 获取所有表单回填配置
     * @return
     */
    List<BackfillConfigDto> getBackfills(Integer entityId, Integer fieldId);

    /**
     * 获取表单回填配置
     * @param id 表单回填配置id
     * @return
     */
    BackfillConfig getBackfill(Integer id);

    /**
     * 删除表单回填配置
     * @param id 表单回填配置id
     * @return
     */
    Integer deleteBackfill(Integer id);

    /**
     * 获取表单回填赋予目标字段的数据
     * @param backfillParam
     * @return
     */
    List<Map<String, Object>> getResourceFieldValue(BackfillParam backfillParam);

    /**
     * 获取excel文件表头信息
     * @param path
     * @return
     */
    List<String> getHeader(String path) throws IOException;

    /**
     * 导入excel
     * @param path 文件路径
     * @param entityId 实体id
     * @param filedIds 字段id集合
     * @return
     */
    Integer importExcel(String path, Integer entityId, String filedIds) throws Exception;
}
