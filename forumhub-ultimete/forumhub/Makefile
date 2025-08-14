.PHONY: run stop logs test build

run:
	docker compose up --build

stop:
	docker compose down

logs:
	docker compose logs -f app

test:
	mvn -q -DskipITs test

build:
	mvn -q -DskipTests package
