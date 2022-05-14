const BoardList = ({boardList}) => {
    console.log(boardList);

    
    return (
        <div className='List-container'>
            {boardList.map((item, index) => (
                <div key={index}>{item.title}</div>
            ))
            
            }
        </div>
    );
}

export default BoardList;