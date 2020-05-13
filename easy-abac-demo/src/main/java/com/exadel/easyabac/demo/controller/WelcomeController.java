/*
 * Copyright 2019-2020 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.exadel.easyabac.demo.controller;

import com.exadel.easyabac.demo.security.authorization.DemoAuthorization;
import com.exadel.easyabac.demo.security.authorization.authentication.AdminAuthentication;
import com.exadel.easyabac.demo.security.authorization.authentication.BusinessAnalystAuthentication;
import com.exadel.easyabac.demo.security.authorization.authentication.DeveloperAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


/**
 * Application welcome controller.
 *
 * @author Igor Sych
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
@Controller
public class WelcomeController {

    private static final String WELCOME_PAGE = "welcome";
    private static final String AUTHORIZED_USER = "authorizedUser";
    private static final String BASE_URL = "baseURL";

    @Autowired
    private DemoAuthorization authorization;

    @GetMapping("/")
    public String root(Model model, HttpServletRequest request) {
        return getWelcomePage(model, request);
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request) {
        authorization.logout();
        return getWelcomePage(model, request);
    }

    @GetMapping("/login-as-admin")
    public String loginAsAdministrator(Model model, HttpServletRequest request) {
        authorization.loginAs(new AdminAuthentication());
        return getWelcomePage(model, request);
    }

    @GetMapping("/login-as-ba")
    public String loginAsBusinessAnalyst(Model model, HttpServletRequest request) {
        authorization.loginAs(new BusinessAnalystAuthentication());
        return getWelcomePage(model, request);
    }

    @GetMapping("/login-as-dev")
    public String loginAsDeveloper(Model model, HttpServletRequest request) {
        authorization.loginAs(new DeveloperAuthentication());
        return getWelcomePage(model, request);
    }

    private String getWelcomePage(Model model, HttpServletRequest request) {
        model.addAttribute(AUTHORIZED_USER, authorization.getLoggedUserRole());
        model.addAttribute(BASE_URL, getBaseURL(request));
        return WELCOME_PAGE;
    }

    private String getBaseURL(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        return requestURL.substring(0, requestURL.length() - request.getRequestURI().length()) + request.getContextPath();
    }
}
