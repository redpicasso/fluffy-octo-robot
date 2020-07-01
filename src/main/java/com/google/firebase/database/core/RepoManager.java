package com.google.firebase.database.core;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.InternalHelpers;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class RepoManager {
    private static final RepoManager instance = new RepoManager();
    private final Map<Context, Map<String, Repo>> repos = new HashMap();

    public static Repo getRepo(Context context, RepoInfo repoInfo) throws DatabaseException {
        return instance.getLocalRepo(context, repoInfo);
    }

    public static Repo createRepo(Context context, RepoInfo repoInfo, FirebaseDatabase firebaseDatabase) throws DatabaseException {
        return instance.createLocalRepo(context, repoInfo, firebaseDatabase);
    }

    public static void interrupt(Context context) {
        instance.interruptInternal(context);
    }

    public static void interrupt(final Repo repo) {
        repo.scheduleNow(new Runnable() {
            public void run() {
                repo.interrupt();
            }
        });
    }

    public static void resume(final Repo repo) {
        repo.scheduleNow(new Runnable() {
            public void run() {
                repo.resume();
            }
        });
    }

    public static void resume(Context context) {
        instance.resumeInternal(context);
    }

    private Repo getLocalRepo(Context context, RepoInfo repoInfo) throws DatabaseException {
        Repo repo;
        context.freeze();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://");
        stringBuilder.append(repoInfo.host);
        stringBuilder.append("/");
        stringBuilder.append(repoInfo.namespace);
        String stringBuilder2 = stringBuilder.toString();
        synchronized (this.repos) {
            if (!(this.repos.containsKey(context) && ((Map) this.repos.get(context)).containsKey(stringBuilder2))) {
                InternalHelpers.createDatabaseForTests(FirebaseApp.getInstance(), repoInfo, (DatabaseConfig) context);
            }
            repo = (Repo) ((Map) this.repos.get(context)).get(stringBuilder2);
        }
        return repo;
    }

    private Repo createLocalRepo(Context context, RepoInfo repoInfo, FirebaseDatabase firebaseDatabase) throws DatabaseException {
        Repo repo;
        context.freeze();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://");
        stringBuilder.append(repoInfo.host);
        stringBuilder.append("/");
        stringBuilder.append(repoInfo.namespace);
        String stringBuilder2 = stringBuilder.toString();
        synchronized (this.repos) {
            if (!this.repos.containsKey(context)) {
                this.repos.put(context, new HashMap());
            }
            Map map = (Map) this.repos.get(context);
            if (map.containsKey(stringBuilder2)) {
                throw new IllegalStateException("createLocalRepo() called for existing repo.");
            }
            repo = new Repo(repoInfo, context, firebaseDatabase);
            map.put(stringBuilder2, repo);
        }
        return repo;
    }

    private void interruptInternal(final Context context) {
        RunLoop runLoop = context.getRunLoop();
        if (runLoop != null) {
            runLoop.scheduleNow(new Runnable() {
                public void run() {
                    synchronized (RepoManager.this.repos) {
                        if (RepoManager.this.repos.containsKey(context)) {
                            Object obj;
                            loop0:
                            while (true) {
                                obj = 1;
                                for (Repo repo : ((Map) RepoManager.this.repos.get(context)).values()) {
                                    repo.interrupt();
                                    if (obj == null || repo.hasListeners()) {
                                        obj = null;
                                    }
                                }
                                break loop0;
                            }
                            if (obj != null) {
                                context.stop();
                            }
                        }
                    }
                }
            });
        }
    }

    private void resumeInternal(final Context context) {
        RunLoop runLoop = context.getRunLoop();
        if (runLoop != null) {
            runLoop.scheduleNow(new Runnable() {
                public void run() {
                    synchronized (RepoManager.this.repos) {
                        if (RepoManager.this.repos.containsKey(context)) {
                            for (Repo resume : ((Map) RepoManager.this.repos.get(context)).values()) {
                                resume.resume();
                            }
                        }
                    }
                }
            });
        }
    }
}
