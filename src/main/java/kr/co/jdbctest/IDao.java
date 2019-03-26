package kr.co.jdbctest;

import java.util.List;

public interface IDao {
	public List<BbsDTO> list();								//목록
	public List<BbsDTO> paging(int start, int end);	//페이징
	public BbsDTO read(int num);							//상세보기
	public int rowCount();										//레코드갯수
	public int insert(BbsDTO dto);							//쓰기
	public void update(BbsDTO dto);						//수정
	public void delete(int num);								//삭제
}//interface end
