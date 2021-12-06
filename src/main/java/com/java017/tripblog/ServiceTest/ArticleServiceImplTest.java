package com.java017.tripblog.ServiceTest;

import com.java017.tripblog.entity.Article;
import com.java017.tripblog.entity.User;
import com.java017.tripblog.repository.ArticleRepository;
import com.java017.tripblog.repository.CollectRepository;
import com.java017.tripblog.repository.RecommendRepository;
import com.java017.tripblog.repository.ReportRepository;
import com.java017.tripblog.service.ArticleService;
import com.java017.tripblog.service_impl.ArticleServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ArticleServiceImplTest {

    @Mock
    private  ArticleRepository articleRepository;

    @Mock
    private  ReportRepository reportRepository;

    @Mock
    private  CollectRepository collectRepository;

    @Mock
    private  RecommendRepository recommendRepository;

    private  ArticleService _sut;


    @Before
    public  void TestInitalize()
    {
        MockitoAnnotations.initMocks(this);
        _sut = new ArticleServiceImpl(articleRepository,reportRepository,collectRepository,recommendRepository);
    }

    @Test
    public void findUserById_輸入null_拋出Exception() {
        //arrange
        //act
        //assert
        Assertions.assertThrows(NullPointerException.class,()->_sut.findUserById(null));
    }

    @Test
    public void findUserById_輸入userId小等於0_傳回null() {
        //arrange
        User user = new User();
        user.setId((long)-1);

        //act
        ArrayList<Article> actual = _sut.findUserById(user);

        //assert
        Assertions.assertNull(actual);
    }

    @Test
    public void findUserById_輸入userId小等於1_傳回對應的文章() {
        //arrange
        User user = new User();
        user.setId((long)1);

        ArrayList<Article> expected  = new  ArrayList<Article>();
        Article article = new  Article();
        article.setUserId(user);
        expected.add(article);

        when(articleRepository.findByUserId(user)).thenReturn(expected);


        //act
        ArrayList<Article> actual = _sut.findUserById(user);

        //assert
        Assertions.assertEquals(expected,actual);

        //大約比較
        //Assertions.assertTrue(new ReflectionEquals(expected).matches(actual));
    }
}