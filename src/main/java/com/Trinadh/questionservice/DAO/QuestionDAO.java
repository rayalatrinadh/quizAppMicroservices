package com.Trinadh.questionservice.DAO;


import com.Trinadh.questionservice.Model.Question;
import com.Trinadh.questionservice.Model.QuestionWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDAO extends JpaRepository<Question,Integer> {

     List<Question> findByCategory(String category);



     //nativeQuery
     @Query(value = "SELECT * FROM question q where q.category=:category ORDER BY RANDOM() LIMIT :numQ",nativeQuery = true)
     List<Question> findRandomQuestionsByCategory(String category, int numQ);

     @Query(value = "Select q.id from question q where q.category=:category order by random() LIMIT :numQuestions",nativeQuery = true)
     List<Integer> findQuestionIdsByCategory(String category, Integer numQuestions);



}
