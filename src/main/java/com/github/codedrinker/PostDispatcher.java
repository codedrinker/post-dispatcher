package com.github.codedrinker;

import com.github.codedrinker.dispatcher.AbstractDispatcher;
import com.github.codedrinker.dispatcher.SegmentFaultDispatcher;
import com.github.codedrinker.entity.DispatchMarkdown;
import com.github.codedrinker.util.DispatcherWebdriver;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codedrinker on 17/02/2018.
 */
public class PostDispatcher {

    private List<AbstractDispatcher> getDispatchers(WebDriver driver) {
        List<AbstractDispatcher> dispatchers = new ArrayList<>();
        dispatchers.add(new SegmentFaultDispatcher(driver));
        return dispatchers;
    }

    public void dispatch(DispatchMarkdown dispatchMarkdown) {
        WebDriver driver = DispatcherWebdriver.getDriver();
        List<AbstractDispatcher> dispatchers = getDispatchers(driver);
        for (AbstractDispatcher dispatcher : dispatchers) {
            dispatcher.post(dispatchMarkdown);
        }
    }

    public static void main(String[] args) {
        PostDispatcher postDispatcher = new PostDispatcher();
        DispatchMarkdown markdown = new DispatchMarkdown();
        markdown.setTitle("Post Dispatcher Title");
        markdown.setContent("Post Dispatcher Content");
        markdown.setTags("java");
        postDispatcher.dispatch(markdown);
    }
}
