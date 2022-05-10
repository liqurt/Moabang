
import "./ThemeCSS/ThemeDetail.css";

import ReviewList from '../Review/ReviewList';
import ReviewWrite from '../Review/ReviewWrite';


const ThemeDetail = (props) => {
    //리뷰 더미 데이터
    

    const Theme = props.Theme

    console.log(Theme);

    //난이도별 열쇠 이미지 개수 맞추기
    const DifficultyKeyImg = (diff) => {
        const result = [];
        for (let i = 0; i < diff; i++) {
            result.push(<img key={i}  id='DiffKey' src='https://www.emojiall.com/images/240/htc/1f511.png' alt='keyimg' />)
        }
        return result;

    }
    //사람 개수 최소
    const ConanMin = (min) => {
        const num = parseInt(min.substr(-3,1));
        const result = [];
        for (let i = 0; i < num; i++) {
            result.push(<img key={i}  id='Conan' src='https://us.123rf.com/450wm/iconmama/iconmama1512/iconmama151200208/49892585-%EC%9D%B8%EA%B0%84-%EC%95%84%EC%9D%B4%EC%BD%98.jpg?ver=6' alt='conanMin' />)
        }
        return result;
        
    }
    //사람 개수 최대
    const ConanMax = (max) => {
        const num = parseInt(max.substr(-1));
        const result = [];
        for (let i = 0; i < num; i++) {
            result.push(<img key={i} id='Conan' src='https://us.123rf.com/450wm/iconmama/iconmama1512/iconmama151200208/49892585-%EC%9D%B8%EA%B0%84-%EC%95%84%EC%9D%B4%EC%BD%98.jpg?ver=6' alt='conanMax' />)
        }
        return result;
    }
    //물결표
    const Water = () => {
        return <img id='water' src='https://upload.wikimedia.org/wikipedia/commons/thumb/4/42/Tilde.svg/1200px-Tilde.svg.png' alt="water" ></img>
    }
    const starScore = () => {
        return <img id='theme-detail-starscore' src='https://emojigraph.org/media/facebook/star_2b50.png' alt='starscore'></img>
    }

    return (
        <div className='ThemeDetailTotal'>
            
            <div className='ThemeDetailImgAndInfo'>
                <img className='TDetailImg' alt='profile' src={Theme.img} />
                
            
                <div className='ThemeDetailInfo'>
                    <div id='ThemeDetailName' >{Theme.tname}</div>
                    <div id='ThemeDetailType'>{Theme.type}</div>
                    <div id='ThemeDetailGenre'>{Theme.genre}</div>
                    <div id='ThemeDetailRplayer'>{ConanMin(Theme.rplayer)}
                            {Water()}{ConanMax(Theme.rplayer)}</div>
                    <div id='ThemeDetailTime'>{Theme.time}</div>
                    <div id='ThemeDetailListDiff'>{DifficultyKeyImg(Theme.difficulty)}</div>
                    <div id='ThemeDetailListGrade'>{starScore()}&nbsp;{Theme.grade}</div>
                </div>
                <div className='ThemeTotalReview'>
                    리뷰통계 - 리뷰 기능 만들면 연결할 예정
                    <div>체감 난이도</div>
                    <div>활동성</div>
                    <div>추천 인원</div>
                    <div>탈출 성공률</div>
                    <div>평균 소요시간</div>
                </div>

            </div>
            <div className='ThemeNav'>
                홈페이지이동|리뷰|비교하기|예약하기
            </div>

            <ReviewWrite tid={Theme.tid}/>
            <div><ReviewList tid={Theme.tid} /></div>

                
        </div>
    )

}

export default ThemeDetail;