package org.project.board.controllers.admins;

import lombok.Data;

@Data
public class ConfigForm {
    private String siteTitle = "";
    private String siteDescription = ""; // 설명
    private String cssJsVersion = "" + 1; // css, js 버전
    private String joinTerms = ""; // 회원가입 약관
}