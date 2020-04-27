package spms.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import spms.annotation.Component;
import spms.vo.Project;

@Component("projectDao")
public class MySqlMyBatisProjectDao implements ProjectDao {

	SqlSessionFactory sqlSessionFactory;

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public List<Project> selectList(HashMap<String, Object> paramMap) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// namespace + id
			return sqlSession.selectList("spms.dao.ProjectDao.selectList", paramMap);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public int insert(Project project) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int count = sqlSession.insert("spms.dao.ProjectDao.insert", project);
			sqlSession.commit();
			return count;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public Project selectOne(int no) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.selectOne("spms.dao.ProjectDao.selectOne", no);
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public int update(Project project) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// 1. DB의 값(original)과 클라이언트가 보내온 값(project)을 비교한다
			// map객체를 생성해서 다른 대상만 map에 저장한다
			Project original = sqlSession.selectOne("spms.dao.ProjectDao.selectOne", project.getNo());

			Hashtable<String, Object> paramMap = new Hashtable<String, Object>();
			if (project.getTitle().equals(original.getTitle()) == false) {
				paramMap.put("title", project.getTitle());
			}
			if (project.getContent().equals(original.getContent()) == false) {
				paramMap.put("content", project.getContent());
			}
			if (project.getStartDate().compareTo(original.getStartDate()) != 0) {
				paramMap.put("startDate", project.getStartDate());
			}
			if (project.getEndDate().compareTo(original.getEndDate()) != 0) {
				paramMap.put("endDate", project.getEndDate());
			}
			if (project.getState() != original.getState()) {
				paramMap.put("state", project.getState());
			}
			if (project.getTags().equals(original.getTags()) == false) {
				paramMap.put("tags", project.getTags());
			}

			// 2. 변경된 값만 update한다
			// 1개라도 변경된 것이 있으면
			if(paramMap.size()>0) {
				paramMap.put("no", project.getNo());
				int count = sqlSession.update("spms.dao.ProjectDao.update",paramMap);
				sqlSession.commit();
				return count;
			}
			// 변경된 것이 없다
			else {
				return 0;
			}

		} finally {
			sqlSession.close();
		}
	}

	@Override
	public int delete(int no) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int count = sqlSession.delete("spms.dao.ProjectDao.delete", no);
			sqlSession.commit();
			return count;
		} finally {
			sqlSession.close();
		}
	}

}
