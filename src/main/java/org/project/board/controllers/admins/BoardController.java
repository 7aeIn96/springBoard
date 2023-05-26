package org.project.board.controllers.admins;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.project.board.commons.MenuDetail;
import org.project.board.commons.Menus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("AdminBoardController")
@RequestMapping("/admin/board")
@RequiredArgsConstructor
public class BoardController {
    private final HttpServletRequest request;

    /**
     * 게시판 목록
     *
     * @return
     */
    @GetMapping
    public String index(Model model) {
        commonProcess(model, "게시판 목록");

        return "admin/board/index";
    }

    /**
     * 게시판 등록
     * @return
     */
    @GetMapping("/register")
    public String register(Model model) {
        commonProcess(model, "게시판 등록");

        return "admin/board/config";
    }

    /**
     * 게시판 수정
     * @return
     */
    @GetMapping("/{bId}/update")
    public String update(@PathVariable String bId, Model model) {
        commonProcess(model, "게시판 수정");

        return "admin/board/config";
    }

    /**
     * 게시글 관리
     * @return
     */
    @GetMapping("/posts")
    public String update() {

        return "admin/board/posts";
    }

    /**
     * 설정 저장, 추가, 수정을 위한 가상 페이지
     * @return
     */
    @PostMapping("/save")
    public String save() {

        return "redirect:/admin/board"; // 게시판 목록
    }
    private void commonProcess(Model model, String title) {
        String URI = request.getRequestURI();

        // 서브 메뉴 처리
        String subMenuCode = Menus.getSubMenuCode(request);
        subMenuCode = title.equals("게시판 수정") ? "register" : subMenuCode;
        model.addAttribute("subMenuCode", subMenuCode);

        List<MenuDetail> submenus = Menus.gets("board");
        model.addAttribute("submenus", submenus);

        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
    }
}
