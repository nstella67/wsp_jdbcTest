package kr.co.jdbctest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class BbsDAO implements IDao {

	// 1) POJO 방식 DB 연결
	//DBOpen dbopen=new DBOpen()

	// 2) 스프링빈 객체주입(DI)
	// jdbc.xml---------------------
	// <property name="jt" ref="jdbcTemplate"></property>
	JdbcTemplate jt;

	// jt 객체이름으로 반드시 setter 함수가 있어야 한다(필수)
	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}

	@Override
	public List<BbsDTO> list() {
		// 전체목록
		StringBuilder sql=new StringBuilder();
		sql.append(" SELECT *");
		sql.append(" FROM board2");
		sql.append(" ORDER BY idx DESC");
		
		//RowMapper=PreparedStatement + ResultSet (+ List)
		RowMapper<BbsDTO> rowMapper=new RowMapper<BbsDTO>() {	//interface 원래 new 못함. RowMapper:interface.

			@Override		//일시적으로 가능하게 해줌	
			public BbsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BbsDTO dto=new BbsDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setName(rs.getString("name"));
				dto.setEmail(rs.getString("email"));
				dto.setHomepage(rs.getString("homepage"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPwd(rs.getString("pwd"));
				dto.setWdate(rs.getString("wdate"));
				dto.setHit(rs.getInt("hit"));
				return dto;
			}//mapRow() end
		};//rowMapper end
		List<BbsDTO> list=jt.query(sql.toString(), rowMapper);
		return list;
	}//list() end

	@Override
	public List<BbsDTO> paging(int start, int end) {	//페이징
		StringBuilder sql=new StringBuilder();
		sql.append(" SELECT *");
		sql.append(" FROM board2");
		sql.append(" WHERE idx>="+start+" AND idx<="+end);
		sql.append(" ORDER BY idx DESC");
		
		RowMapper<BbsDTO> rowMapper=new RowMapper<BbsDTO>() {

			@Override
			public BbsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BbsDTO dto=new BbsDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setName(rs.getString("name"));
				dto.setEmail(rs.getString("email"));
				dto.setHomepage(rs.getString("homepage"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPwd(rs.getString("pwd"));
				dto.setWdate(rs.getString("wdate"));
				dto.setHit(rs.getInt("hit"));
				return dto;
			}//mapRow() end
		};//rowMapper end
		List<BbsDTO> list=jt.query(sql.toString(), rowMapper);
		return list;
	}//paging() end

	@Override
	public BbsDTO read(int num) {	//상세보기
		StringBuilder sql=new StringBuilder();
		sql.append(" SELECT * FROM board2");
		sql.append(" WHERE idx="+num);
		BbsDTO dto=null;
		RowMapper<BbsDTO> rowMapper=new RowMapper<BbsDTO>() {

			@Override
			public BbsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BbsDTO dto=new BbsDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setName(rs.getString("name"));
				dto.setEmail(rs.getString("email"));
				dto.setHomepage(rs.getString("homepage"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPwd(rs.getString("pwd"));
				dto.setWdate(rs.getString("wdate"));
				dto.setHit(rs.getInt("hit"));
				return dto;
			}//mapRow() end
		};//rowMapper end
		
		dto=jt.queryForObject(sql.toString(), rowMapper);
		return dto;
	}//read() end

	@Override
	public int rowCount() {	//레코드갯수
		StringBuilder sql=new StringBuilder();
		sql.append(" SELECT COUNT(*) FROM board2");
		return jt.queryForObject(sql.toString(), Integer.class);
	}//rowCount() end

	@Override
	public int insert(BbsDTO dto) {
		// 행추가
		StringBuilder sql=new StringBuilder();
		sql.append(" INSERT INTO board2(idx, name, email, homepage, title, content, pwd, wdate, hit)");
		sql.append(" VALUES(board2_idx_seq.nextval");
		sql.append(" , '"+dto.getName()+"'");
		sql.append(" , '"+dto.getEmail()+"'");
		sql.append(" , '"+dto.getHomepage()+"'");
		sql.append(" , '"+dto.getTitle()+"'");
		sql.append(" , '"+dto.getContent()+"'");
		sql.append(" , '"+dto.getPwd()+"'");
		sql.append(" , sysdate");
		sql.append(" , "+dto.getHit()+")");
		System.out.println(sql.toString());
		int cnt=jt.update(sql.toString());
		return cnt;
	}//insert() end

	@Override
	public void update(BbsDTO dto) {	//수정
		StringBuilder sql=new StringBuilder();
		sql.append(" UPDATE board2");
		sql.append(" SET name='"+dto.getName()+"'");
		sql.append(" WHERE idx="+dto.getIdx());
		jt.update(sql.toString());
	}//update() end

	@Override
		public void delete(int num) { //삭제
			StringBuilder sql=new StringBuilder();
			sql.append(" DELETE FROM board2");
			sql.append(" WHERE idx="+num);
			jt.update(sql.toString());
	}//delete() end

}
