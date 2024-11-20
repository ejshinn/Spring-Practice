const deleteButton = document.querySelector("#delete-button");

if(deleteButton) {
    deleteButton.addEventListener("click", e => {
        let id = document.querySelector("#article-id").value;

        // fetch() : 자바스크립트에서 자체적으로 제공하는 비동기 통신 모듈
        // axios와 동일한 기능을 함
        fetch(`/api/articles/${id}`, {
            method: "DELETE"
        })
            .then(() => {
                alert("삭제되었습니다.");
                // 지정한 페이지로 이동
                location.replace("/articles")
            });
    });
}

const modifyButton = document.querySelector("#modify-button");

if(modifyButton) {
    modifyButton.addEventListener("click", e => {
        let params = new URLSearchParams(location.search);
        let id = params.get("id");

        fetch(`/api/articles/${id}`, {
            method: "PUT",
            // 서버로 전달할 데이터의 타입을 설정
            headers: {
                "Content-Type": "application/json"
            },
            // json 문자열로 변환
            body: JSON.stringify({
                title: document.querySelector("#title").value,
                content: document.querySelector("#content").value
            })
        })
            .then(() => {
                alert("수정이 완료되었습니다.");
                location.replace(`/articles/${id}`);
            });
    });
}

const createButton = document.querySelector("#create-button");
if(createButton) {
    createButton.addEventListener("click", e => {
        fetch("/api/articles", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title: document.querySelector("#title").value,
                content: document.querySelector("#content").value
            })
        })
            .then(() => {
                alert("생성이 완료되었습니다.");
                location.replace(`/articles`);
            });
    });
}