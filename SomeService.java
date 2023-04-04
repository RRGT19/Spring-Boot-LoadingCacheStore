import com.example.shared.LoadingCacheStore;
import com.google.common.cache.CacheLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SomeService {

    private final LoadingCacheStore<List<String>> offersCache;

    public StrapiService() {
        offersCache = new LoadingCacheStore<>(1, TimeUnit.HOURS, new CacheLoader<>() {
            @Override
            public List<String> load(String key) throws Exception {
                return fetchOffers();
            }
        });
    }

    public List<String> getOffers() {
        try {
            return offersCache.get("offers");
        } catch (ExecutionException e) {
            log.error("ExecutionException", e);
            return Collections.emptyList();
        }
    }

    private List<String> fetchOffers() {
        return Collections.emptyList();
    }

}
