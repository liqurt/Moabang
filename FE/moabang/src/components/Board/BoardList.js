const BoardList = ({boardList}) => {
    console.log(boardList);

    
    return (
        <div>
            {boardList.map((item, index) => (
                <div key={index}>{item.title}</div>
            ))
            
            }
        </div>
    );
}

export default BoardList;