package org.project.board.controllers.admins;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.board.commons.CommonException;
import org.project.board.commons.MenuDetail;
import org.project.board.commons.Menus;
import org.project.board.entities.Board;
import org.project.board.models.board.config.BoardConfigInfoService;
import org.project.board.models.board.config.BoardConfigListService;
import org.project.board.models.board.config.BoardConfigSaveService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("AdminBoardController")
@RequestMapping("/admin/board")
@RequiredArgsConstructor
public class BoardController {
    private final HttpServletRequest request;
    private final BoardConfigSaveService configSaveService;
    private final BoardConfigInfoService configInfoService;
    private final BoardConfigListService configListService;

    /**
     * 게시판 목록
     *
     * @return
     */
    @GetMapping
    public String index(@ModelAttribute BoardSearch boardSearch, Model model) {
        commonProcess(model, "게시판 목록");
        Page<Board> data = configListService.gets(boardSearch);
        model.addAttribute("items", data.getContent());

        return "admin/board/index";
    }

    /**
     * 게시판 등록
     * @return
     */
    @GetMapping("/register")
    public String register(@ModelAttribute BoardForm boardForm, Model model) {
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

        Board board = configInfoService.get(bId, true);
        BoardForm boardForm = new ModelMapper().map(board, BoardForm.class);
        boardForm.setMode("update"); // 모드 update로 설정
        boardForm.setListAccessRole(board.getListAccessRole().toString());
        boardForm.setViewAccessRole(board.getViewAccessRole().toString());
        boardForm.setWriteAccessRole(board.getWriteAccessRole().toString());
        boardForm.setReplyAccessRole(board.getReplyAccessRole().toString());
        boardForm.setCommentAccessRole(board.getCommentAccessRole().toString());

        model.addAttribute("boardForm", boardForm);

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
    public String save(@Valid BoardForm boardForm, Errors errors, Model model) {
        String mode = boardForm.getMode();
        commonProcess(model, mode != null && mode.equals("update") ? "게시판 수정" : "게시판 등록");
        try {
            configSaveService.save(boardForm, errors); // 검증된 에러가 발견되지않으면 save 후 끝!
        } catch (CommonException e) { //
            errors.reject("BoardConfigError", e.getMessage());
        }

        if (errors.hasErrors()) { // 에러가 있으면 ( 필수 항목 체크만 하니까 별도의 Validator 필요 X )
            return "admin/board/config";
        }
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
