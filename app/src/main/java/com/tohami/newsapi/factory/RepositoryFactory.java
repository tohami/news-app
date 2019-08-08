package com.tohami.newsapi.factory;

import com.tohami.newsapi.base.BaseRepository;
import com.tohami.newsapi.data.retrofit.ApiInterface;

import java.lang.reflect.InvocationTargetException;

public class RepositoryFactory {
    private final ApiInterface apiInterface;

    public RepositoryFactory(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public <T extends BaseRepository> T create(Class<T> repositoryClass) {
        if (BaseRepository.class.isAssignableFrom(repositoryClass)) {
            try {
                return repositoryClass.getConstructor(ApiInterface.class).newInstance(apiInterface);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot create an instance of " + repositoryClass, e);
            } catch (InstantiationException e) {
                throw new RuntimeException("Cannot create an instance of " + repositoryClass, e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Cannot create an instance of " + repositoryClass, e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Cannot create an instance of " + repositoryClass, e);
            }
        }
        throw new IllegalArgumentException("Unknown Reposotiry class " + repositoryClass);

    }
}