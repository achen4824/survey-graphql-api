package org.technology.consilium.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.technology.consilium.data.model.SurveyTemplate;
import org.technology.consilium.data.model.questions.Question;
import org.technology.consilium.data.repositories.SurveyTemplateRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SurveyTemplateController {

    @Autowired
    SurveyTemplateRepository surveyTemplateRepository;

    @RequestMapping(
            value = "/surveytemplates",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @CrossOrigin
    public List<SurveyTemplate> getSurveyTemplates() {
        List<SurveyTemplate> surveyTemplates = new ArrayList<>();
        surveyTemplateRepository.findAll().forEach(surveyTemplates::add);
        return surveyTemplates;
    }
}
