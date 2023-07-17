package com.Trinadh.questionservice.Controller;


import com.Trinadh.questionservice.Model.Question;
import com.Trinadh.questionservice.Model.QuestionWrapper;
import com.Trinadh.questionservice.Model.SubmitQuestion;
import com.Trinadh.questionservice.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() throws SQLException {
        return questionService.questionService();
    }


    @GetMapping("category/{categoryType}")
    public ResponseEntity<List<Question>> getAllQuestionsByCategory(@PathVariable String categoryType){
            return questionService.getAllQuestionsByCategory(categoryType);
    }


    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){

        System.out.println(" get question from client is : " + question);
            return questionService.addQuestion(question);
    }

    //task 1:
    // get question ids : just get category and num of questions from quiz service and return respective category question ids
    @GetMapping("/getQuestionIdsForQuiz")
    public ResponseEntity<List<Integer>> getQuestionIdsForQuiz(@RequestParam String category, @RequestParam Integer numQuestions){
        return questionService.getQuesionIdsForQuiz(category,numQuestions);
    }

    //task 2:
    //now quiz services send the question id then return the questions to the quiz service
    //please note we dont need to send the answer . just we need to send the question and it's options only.

    @PostMapping("getQuestionsByIds")
    public ResponseEntity<List<QuestionWrapper>> getQuestionByIds(@RequestBody List<Integer> ids){
        return questionService.getQuestionByIds(ids);
    }

    //task 3:
    //quiz service send the quiz answer to the question service so, in the question service need to calculate the score
    //and then return score to the quiz service.

    @PostMapping("submitResponses")
    public ResponseEntity<Integer> submitResponsesToGetScore(@RequestBody List<SubmitQuestion> responses){
        return questionService.getScore(responses);
    }

}
