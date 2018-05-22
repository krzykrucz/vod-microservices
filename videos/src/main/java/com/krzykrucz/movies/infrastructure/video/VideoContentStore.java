package com.krzykrucz.movies.infrastructure.video;

import org.springframework.content.commons.repository.ContentStore;

import java.util.UUID;

interface VideoContentStore extends ContentStore<PersistentVideoInfo, UUID> {
}
