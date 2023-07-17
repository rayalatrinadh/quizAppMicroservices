package com.Trinadh.questionservice.Service;


import com.Trinadh.questionservice.DAO.QuestionDAO;
import com.Trinadh.questionservice.Model.Question;
import com.Trinadh.questionservice.Model.QuestionWrapper;
import com.Trinadh.questionservice.Model.SubmitQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDAO questionDAO;

    public ResponseEntity<List<Question>> questionService()  {
        try{
            return new ResponseEntity<>(questionDAO.findAll(),HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }

        //if exception occurs need to send bad Request
        return new ResponseEntity<>(new ArrayList(),HttpStatus.BAD_REQUEST);

    }


    public ResponseEntity<List<Question>> getAllQuestionsByCategory(String categoryType) {
        try{
            return new ResponseEntity<>(questionDAO.findByCategory(categoryType),HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question){
        try{
            questionDAO.save(question);
            //HttpStatus.CREATED -> because the question created in the database .
            return new ResponseEntity<>("success",HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
        }
            return new ResponseEntity<>("failure",HttpStatus.NOT_ACCEPTABLE);
    }


    public ResponseEntity<List<Integer>> getQuesionIdsForQuiz(String category, Integer numQuestions) {
        try{
            return new ResponseEntity<>(questionDAO.findQuestionIdsByCategory(category,numQuestions),HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionByIds(List<Integer> ids) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for(Integer id : ids){
             questions.add(questionDAO.findById(id).get());
        }

        System.out.println("questions : " + questions);

        for(Question que : questions){
            QuestionWrapper wrap = new QuestionWrapper();
            wrap.setId(que.getId());
            wrap.setQuestionTitle(que.getQuestionTitle());
            wrap.setOption1(que.getOption1());
            wrap.setOption2(que.getOption2());
            wrap.setOption3(que.getOption3());
            wrap.setOption4(que.getOption4());
            wrappers.add(wrap);

        }
        return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<SubmitQuestion> responses) {

        int count = 0;
        for(SubmitQuestion response : responses){
           Question question =  questionDAO.findById(response.getId()).get();
           if(question.getRightAnswer().equalsIgnoreCase(response.getResponse()))
               count++;

        }
        return new ResponseEntity<>(count,HttpStatus.OK);
    }
}
