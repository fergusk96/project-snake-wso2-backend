function seed(dbName, user, password) {
  db = db.getSiblingDB(dbName);
  db.getCollection("game").createIndex({ winnerId: 1 });
  db.createUser({
    user: user,
    pwd: password,
    roles: [{ role: "readWrite", db: dbName }],
  });
}

const dbName = "project-snake";
const user = process.env.DB_USER;
const password = process.env.DB_USER_PWD;
seed(dbName, user, password);