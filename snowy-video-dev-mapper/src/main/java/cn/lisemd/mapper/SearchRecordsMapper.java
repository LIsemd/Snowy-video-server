package cn.lisemd.mapper;

import cn.lisemd.pojo.SearchRecords;
import cn.lisemd.utils.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRecordsMapper extends MyMapper<SearchRecords> {

    public List<String> getHotwords();

}