package com.ohgiraffers.section01.xmlconfig;

import java.util.List;
import java.util.Map;

/* 필기. 컨트롤러는 사용자의 입력값이 있으면 하나로 뭉침. */
public class MenuController {

    private final MenuService menuService;         // 아주 강한 강결합(반드시 이 객체를 주입 받아야 한다, 컨트롤러 다음에는 서비스가 온다)
    private final PrintResult printResult;          // 결과 페이지에 해당하는 (View 개념의) 클래스

    public MenuController() {
        menuService = new MenuService();
        printResult = new PrintResult();
    }

    /* 필기. 핸들러 메소드 느낌 (DB에서 값을 가지고 돌아옴)*/
    public void findAllMenus() {

        List<MenuDTO> menuList = menuService.findAllMenus();  // 값을 가져올 메뉴가 여러개임을 가정하고 List 형식으로 만듬.

        if(!menuList.isEmpty()) {       // menuList에 값이 없어도 menuList는 빈 상태로 넘어오기 때문에 !=null 사용하면 안됨.
            printResult.printMenus(menuList);
        } else {
            printResult.printErrorMessage("조회할 메뉴가 없습니다.");
        }
    }
    public void findMenuByMenuCode(Map<String, String> parameter) {

        int menuCode = Integer.valueOf(parameter.get("menuCode"));      // 형변환 후 menuCode라는 키 값을 가지고 꺼내옴

        MenuDTO menu = menuService.findMenuByMenuCode(menuCode);

        if(menu != null) {
            printResult.printMenu(menu);
        } else {
            printResult.printErrorMessage(menuCode + "번의 메뉴는 없습니다.");
        }
    }

    public void registMenu(Map<String, String> parameter) {
        String menuName = parameter.get("menuName");
        int menuPrice = Integer.valueOf(parameter.get("menuPrice"));
        int categoryCode = Integer.valueOf(parameter.get("categoryCode"));

        MenuDTO menu = new MenuDTO();
        menu.setMenuName(menuName);
        menu.setMenuPrice(menuPrice);
        menu.setCategoryCode(categoryCode);

        if(menuService.registMenu(menu)) {
            printResult.printSuccessMessage("regist");
        } else {
            printResult.printErrorMessage("메뉴 추가 실패!");
        }
    }

    public void modifyMenu(Map<String, String> parameter) {
        int menuCode = Integer.valueOf(parameter.get("menuCode"));
        String menuName = parameter.get("menuName");
        int menuPrice = Integer.valueOf(parameter.get("menuPrice"));

        MenuDTO menu = new MenuDTO();
        menu.setMenuCode(menuCode);
        menu.setMenuName(menuName);
        menu.setMenuPrice(menuPrice);

        if(menuService.modifyMenu(menu)) {
            printResult.printSuccessMessage("modify");
        } else {
            printResult.printErrorMessage("메뉴 수정 실패!");
        }
    }

    public void removeMenu(Map<String, String> parameter) {
        int menuCode = Integer.valueOf(parameter.get("menuCode"));
        if(menuService.removeMenu(menuCode)) {
            printResult.printSuccessMessage("remove");
        } else {
            printResult.printErrorMessage("메뉴 삭제 실패!");
        }
    }
}
