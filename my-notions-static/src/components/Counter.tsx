import React, { useState } from 'react';
import * as classes from './Counter.module.scss';

const Counter: React.FC = () => {
    const [count, setCount] = useState(0);

    const increment = () => {
        setCount(count + 1);
    }

    const decrement = () => {
        setCount(count - 1);
    }

    return (
        <div className={classes.wrapper}>
            <h1>Counter</h1>
            <p>Current count: {count}</p>

            <button className={classes.btn} onClick={() => increment()}>+1</button>
            <button onClick={() => decrement()}>-1</button>
        </div>
    );
}

export default Counter;