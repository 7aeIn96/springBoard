package org.project.board.controllers.admins;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.project.board.commons.configs.ConfigInfoService;
import org.project.board.commons.configs.ConfigSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/config")
public class ConfigController {
    private final ConfigSaveService saveService;
    private final ConfigInfoService infoService;
    private String code = "siteConfig";

    @GetMapping
    public String config(Model model) {
        commonProcess(model); // 공통 처리
        ConfigForm configForm = infoService.get(code, ConfigForm.class);
        /* DB에 들어오는지 확인위해 ( 확인 후 삭제해도 됨 )
        if (configForm != null) {
            log.info(configForm.toString());
        }
        */
        model.addAttribute("configForm", configForm == null ? new ConfigForm() : configForm);
        // DB에 값이 없으면 폼 생성, 있으면 가져옴 = 사이트 설정

        return "admin/config";
    }

    @PostMapping
    public String configPs(ConfigForm configForm, Model model) {
        commonProcess(model); // 공통 처리

        saveService.save(code, configForm);

        model.addAttribute("message", "설정이 저장되었습니다.");

        return "admin/config";
    }

    /** 공통적으로 필요한 값, 메뉴코드 활성화 처리 */
    private void commonProcess(Model model) {
        String title = "사이트 설정";
        String menuCode = "config";
        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
        model.addAttribute("menuCode", menuCode);
    }
}
