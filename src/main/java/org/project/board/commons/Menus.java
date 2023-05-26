package org.project.board.commons;

/**
 * 서브 메뉴 조회
 */

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 서브 메뉴 조회
 */

public class Menus {

    public static List<MenuDetail> gets(String code) {
        List<MenuDetail> menus = new ArrayList<>();

        // 게시판 하위 메뉴
        if (code.equals("board")) {
            menus.add(new MenuDetail("board", "게시판 목록", "/admin/board"));
            menus.add(new MenuDetail("register", "게시판 등록 / 수정", "/admin/board/register"));
            menus.add(new MenuDetail("posts", "게시글 관리", "/admin/board/posts"));
        }
        return menus;
    }
    public static String getSubMenuCode(HttpServletRequest request) { // URL 패턴가지고 잘라서 사용
        String URI = request.getRequestURI();

        return URI.substring(URI.lastIndexOf('/') + 1);
    }
}