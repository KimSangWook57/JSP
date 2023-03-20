package model2.mvcboard;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import fileupload.FileUtil;
import utils.JSFunction;

@WebServlet("/mvcboard/edit.do")
public class EditController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idx = req.getParameter("idx");
		MVCBoardDAO dao = new MVCBoardDAO();
		MVCBoardDTO dto = dao.selectView(idx);
		req.setAttribute("dto", dto);
		req.getRequestDispatcher("/14MVCBoard/Edit.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String saveDirecory = req.getServletContext().getRealPath("/Uploads");
		
		ServletContext application = getServletContext();
		int maxPostSize = Integer.parseInt(application.getInitParameter("maxPostSize"));
		
		MultipartRequest mr = FileUtil.uploadFile(req, saveDirecory, maxPostSize);
		
		if (mr == null) { // 파일 업로드 실패.
			JSFunction.alertBack(resp, "제한 용량 초과");
			return;
		}
		// 수정할 내용들.
		String idx = mr.getParameter("idx");
		String prevOfile = mr.getParameter("prevOfile");
		String prevSfile = mr.getParameter("prevSfile");

		String name = mr.getParameter("name");
		String title = mr.getParameter("title");
		String content = mr.getParameter("content");
		// 비밀번호를 세션에서 받아온다.
		HttpSession session = req.getSession();
		String pass = (String) session.getAttribute("pass");
		
		// DTO에 저장.
		MVCBoardDTO dto = new MVCBoardDTO();
		dto.setIdx(idx);
		dto.setName(name);
		dto.setTitle(title);
		dto.setContent(content);
		dto.setPass(pass);
		
		// ofile과 sfile 이름 설정.
		String fileName = mr.getFilesystemName("ofile");
		if (fileName != null) {
			// 새로운 파일이름 생성.
			String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
			String ext = fileName.substring(fileName.lastIndexOf("."));
			String newFileName = now + ext;
			
			// 파일명 변경
			File oldFile = new File(saveDirecory + File.separator + fileName); 
			File newFile = new File(saveDirecory + File.separator + newFileName); 
			oldFile.renameTo(newFile);
			
			// DTO에 저장.
			dto.setOfile(fileName);
			dto.setSfile(newFileName);
			
			// 기존 파일 삭제.
			FileUtil.deleteFile(req, "/Uploads", prevSfile);
			
		}
		else { // 첨부 차일이 없으면 기존 이름 유지.
			dto.setOfile(prevOfile);
			dto.setSfile(prevSfile);
		}
		
		// 수정 내용을 DB에 반영.
		MVCBoardDAO dao = new MVCBoardDAO();
		int result = dao.updatePost(dto);
		dao.close();
		
		// 성공 or 실패?
		if (result == 1) { // 수정 성공 
			session.removeAttribute("pass");
			resp.sendRedirect("../mvcboard/view.do?idx=" + idx);
		}
		else { // 수정 실패
			JSFunction.alertLocation(resp, "비밀번호 검증을 다시 진행해주세요.", "../mvcboard/view.do?idx=" + idx);
		}
		
	}
}
