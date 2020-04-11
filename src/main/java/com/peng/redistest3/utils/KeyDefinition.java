package com.peng.redistest3.utils;

public interface KeyDefinition {
    public static final String ID_GENERATOR_FOR_ARTICLE="articleIdSeq";
    public static final String ID_GENERATOR_FOR_USER="userIdSeq";
    public static final String ID_GENERATOR_FOR_TAG="tagIdSeq";
    public static final String HASH_KEY_FOR_ARTICLE_PREFIX="article:";
    public static final String KEY_FOR_USER_PREFIX="user:info:";
    public static final String KEY_FOR_TAG_PREFIX="tag:info:";
    public static final String SET_KEY_FOR_USER_ATICLES_PREFIX="user:articles:";
    public static final String SET_KEY_FOR_TAG_ATICLES_PREFIX="tag:articles:";
    public static final String ZSET_KEY_FOR_ATICLES_SORTED_ON_TIME="sortedArticles:time";
    public static final String ZSET_KEY_FOR_ATICLES_SORTED_ON_VOTES="sortedArticles:votes";
    public static final String SET_KEY_FOR_ARTICLE_VOTED_USERS_PREFIX="users:voted:";
}
